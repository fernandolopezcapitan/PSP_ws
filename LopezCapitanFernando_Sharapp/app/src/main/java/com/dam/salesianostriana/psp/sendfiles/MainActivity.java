package com.dam.salesianostriana.psp.sendfiles;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.salesianostriana.psp.sendfiles.SharappMessage;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static int FILE_CODE = 1;
    private static int CODE_CONNECTION = 1;
    private static int CODE_SEND = 2;
    private static int CODE_DESCONNECTION = 3;
    Adapter mensajeAdapter;
    ArrayList<Mensaje> listaM;
    String nameUser;
    Socket s;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    BufferedInputStream bis;
    ByteArrayOutputStream baos;
    ExecutorService executorService;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ListView listaV;


    android.os.Handler puente = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            SharappMessage sharappMessage = (SharappMessage) msg.obj;

            String username = sharappMessage.userName;
            Date fecha = sharappMessage.date;
            Bitmap bitmap = Utils.decodeBitmapSize(sharappMessage.content, 300);

            Log.i("HANDLER_USERNAME", sharappMessage.userName);
            Log.i("HANDLER_DATE", sharappMessage.date.toString());

            Utils.saveImage(MainActivity.this, sharappMessage.fileName, sharappMessage.content);

            Mensaje mensaje = new Mensaje(username, fecha,bitmap);
            mensajeAdapter.add(mensaje);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaV = (ListView) findViewById(R.id.listView);

        preferences = getSharedPreferences("sharapp", this.MODE_PRIVATE);
        editor = preferences.edit();

        listaM = new ArrayList<>();
        mensajeAdapter = new Adapter(this, listaM);
        listaV.setAdapter(mensajeAdapter);

        nameUser = preferences.getString("user",null);

        new OpenConnection().execute(nameUser);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // This always works
                Intent i = new Intent(MainActivity.this, FilePickerActivity.class);
                // This works if you defined the intent filter
                // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                // Set these depending on your use case. These are the defaults.
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
                //i.putExtra(FilePickerActivity.)

                // Configure initial directory by specifying a String.
                // You could specify a String like "/storage/emulated/0/", but that can
                // dangerous. Always use Android's API calls to get paths to the SD-card or
                // internal memory.
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

                startActivityForResult(i, FILE_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                // For JellyBean and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                            // Do something with the URI
                            Toast.makeText(this, "Transmitiendo el fichero " + uri.getPath(), Toast.LENGTH_SHORT).show();
                            new SendFileTask().execute(nameUser,uri.getPath());
                        }
                    }
                    // For Ice Cream Sandwich
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra
                            (FilePickerActivity.EXTRA_PATHS);

                    if (paths != null) {
                        for (String path: paths) {
                            Uri uri = Uri.parse(path);
                            // Do something with the URI
                            Toast.makeText(this, "Transmitiendo el fichero " + uri.getPath(), Toast.LENGTH_SHORT).show();
                            new SendFileTask().execute(nameUser,uri.getPath());

                        }
                    }
                }

            } else {
                Uri uri = data.getData();
                // Do something with the URI
                Toast.makeText(this, "Transmitiendo el fichero " + uri.getPath(), Toast.LENGTH_SHORT).show();
                new SendFileTask().execute(nameUser,uri.getPath());
            }
        }
    }

    private class OpenConnection extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            if (params != null) {


                try {

                    //s = new Socket("192.168.1.34", 10000);
                    s = new Socket("172.27.60.8", 10000);

                    oos = new ObjectOutputStream(s.getOutputStream());
                    ois = new ObjectInputStream(s.getInputStream());

                    SharappMessage sharappMessage = new SharappMessage();
                    sharappMessage.typeMessage = CODE_CONNECTION;
                    sharappMessage.userName = params[0];

                    oos.writeObject(sharappMessage);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            executorService = Executors.newCachedThreadPool();
            executorService.execute(new Recieve(ois,puente));
        }
    }

    private class SendFileTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            if (params != null) {

                String nomRecibido = params[0];
                String rutaImagen = params[1];

                try {
                    int TAM = 64 * 1024;

                    bis = new BufferedInputStream(new FileInputStream(rutaImagen));
                    baos = new ByteArrayOutputStream();

                    byte[] buffer = new byte[TAM];
                    int c;

                    while ((c = bis.read(buffer, 0, TAM)) != -1) {
                        baos.write(buffer, 0, c);
                    }

                    SharappMessage sharappMessage = new SharappMessage();
                    sharappMessage.typeMessage = CODE_SEND;
                    sharappMessage.userName = nomRecibido;
                    sharappMessage.fileName = rutaImagen.split("/")[rutaImagen.split("/").length - 1];
                    sharappMessage.content = baos.toByteArray();
                    sharappMessage.message = "";
                    sharappMessage.date = Calendar.getInstance().getTime();

                    oos.writeObject(sharappMessage);

                    bis.close();
                    baos.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class CloseConnection extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            if (params != null) {

                try {

                    SharappMessage sharappMessage = new SharappMessage();
                    sharappMessage.typeMessage = CODE_DESCONNECTION;
                    sharappMessage.userName = params[0];

                    oos.writeObject(sharappMessage);

                    oos.close();
                    ois.close();
                    s.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CloseConnection().execute(nameUser);
    }
}
