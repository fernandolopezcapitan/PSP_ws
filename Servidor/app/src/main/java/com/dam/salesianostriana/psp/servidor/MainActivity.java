package com.dam.salesianostriana.psp.servidor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView escribe;
    Button btn_enviar;
    ListView lista;
    Socket s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        escribe = (TextView) findViewById(R.id.editText);
        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        lista = (ListView) findViewById(R.id.listView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // Inicializamos el socket, conectandonos a la IP del servidor y del puerto adecuado
                    //Socket s = new Socket("172.27.0.41",10000); IP de Luismi
                    s = new Socket("172.27.60.8", 10000);

                    // Construimos el flujo más adecuado sobre el flujo de salida del socket
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

                    // enviamos la info
                    printWriter.println(escribe.getText().toString());

                    // enviamos el mensaje FIN de comunicación ( en este caso "FIN")
                    printWriter.println("FIN");

                    // forzamos el envío
                    printWriter.flush();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AsyncTask
            }
        });

        class recibirMsg extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;
                String mensaje = null;

                try {
                    bufferedReader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            s.getInputStream()));

                    while (!((mensaje = bufferedReader.readLine())
                            .equalsIgnoreCase("FIN"))) {

                        System.out.println(">> " + mensaje);

                        Log.d("Mensaje", mensaje);
                    }

                    s.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return mensaje;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



            }
        }
        // cerramos el socket y con ello los socket definidos con él

    }
        @Override
        protected void onDestroy () {
            super.onDestroy();
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
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
}
