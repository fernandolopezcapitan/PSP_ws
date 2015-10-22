package com.salesianostriana.dam.psp.ejhandler;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    //Botón que lanzará el diálogo
    Button btn_lanzar;
    //Diálogo con la barra de progreso
    ProgressDialog progressDialog;

    //Texto para el resultado
    TextView txt_resultado;

    /*
        Esta clase es la que es capaz de recibir mensajes desde un hilo
        secundario, y pasarlos hacia el hilo UI.

        Recogiendo el contenido del mensaje, lo podemos mostrar en la
        interfaz de usuario.
     */
    Handler puente = new Handler() {

        //Este es el método que hay que sobreescribir. Aquí dentro
        //escribimos el código que recoge la información del mensaje
        //y la muestra, de manera conveniente, en la interfaz de usuario.
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BigInteger resultado = (BigInteger) msg.obj;
            progressDialog.dismiss();
            txt_resultado.setText(resultado.toString());

        }
    };

    ExecutorService executor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor = Executors.newFixedThreadPool(1);

        txt_resultado = (TextView) findViewById(R.id.txt_resultado);

        btn_lanzar = (Button) findViewById(R.id.btn_lanzar);

        btn_lanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Estas líneas sirven para inicializar el diálogo
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Generando...");
                progressDialog.setMax(100);
                progressDialog.setCancelable(false);
                progressDialog.show();


                //Esta es la tarea en segundo plano.
                //Para no bloquear el hilo UI, lanzamos
                //en un nuevo hilo, un Callable, que nos
                //servirá para calcular un numero que, con
                //un alto nivel de probabilidad, será primo,
                //y así lo podremos mostrar en la UI.
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        //Recogemos el future devuelto por el ejectur
                        Future<BigInteger> f = executor.submit(new ProbablePrimeTask());
                        //Inicializamos el resutado a null (nos valdrá
                        //para comprobar si hay habido un error en la operación)
                        BigInteger result = null;

                        try {
                            //Obtenemos el resultado
                            result = f.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }

                        //Creamos la nueva instancia de mensaje
                        Message m = new Message();
                        //Le asignamos el contenido
                        m.obj = result;
                        //Enviamos el mensaje
                        puente.sendMessage(m);


                    }
                }).start();



            }
        });


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
        executor.shutdown();
    }
}