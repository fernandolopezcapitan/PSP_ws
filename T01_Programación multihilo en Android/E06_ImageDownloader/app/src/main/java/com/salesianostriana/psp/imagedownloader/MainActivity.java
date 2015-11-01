package com.salesianostriana.psp.imagedownloader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button handler, asynctask;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asynctask = (Button) findViewById(R.id.btn_AsyncTask);
        asynctask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProgressDialogTask().execute();
            }
        });
        findViewById(R.id.btn_AsyncTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AsyncTaskActivity.class));
            }
        });
    }
    /*
        Hemos escogido los siguientes tipos por diversas razones:

        - (1) Params = Void:
              Esta tarea solo sirve para mostrar un diálogo y ocultarlo al finalizar
              Como no necesita ningún parámetro para inicializarse, usaremos Void
        - (2) Progress = Integer:
              Durante la ejecución de la tarea, iremos generando números enteros,
              que nos servirán para mostrar el progreso de la misma. Por ello
              usaremos Integer.
        - (3) Result = Void:
              Esta tarea solo no genera ningún resultado final; por ello, usaremos Void
     */
    private class ProgressDialogTask extends AsyncTask<Void, Integer, Void> {

        /*
            Paso 1:
                - Se ejecuta en el hilo UI
                - Sirve para inicializar algún elemento de la interfaz de usuario
         */

        @Override
        protected void onPreExecute() {
            //Estas líneas sirven para inicializar el diálogo

            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Cargando...");
            progressDialog.setMax(10000);
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        /*
            Paso 2:
                - Se ejecuta en un hilo independiente
                - Aquí se realiza, de forma efectiva, la tarea
         */
    @Override
    protected Void doInBackground(Void... params) {

        for(int i = 1; i<= 10000; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //¡¡IMPORTANTE!! Jamás se invoca directamente el método onProgressUpdate
            //La manera conveniente de publicar una actualización del progreso en la UI
            //es a través de este método.
            publishProgress(i);
        }

        return null;
    }

    /*
         Paso 3:
            - Se ejecuta en el hilo UI
            - Sirve para actualizar la interfaz de usuario
    */
    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }


        /*
            Paso 4:
                - Se ejecuta en el hilo UI
                - Recoge el resultado de la tarea, y podemos actualizar
                  la interfaz.
         */

    @Override
    protected void onPostExecute(Void aVoid) {
        //Ocultamos el diálogo, que ya no nos sirve.
        progressDialog.dismiss();
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
