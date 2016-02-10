package com.salesianostriana.psp.firstsocketsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ejecutamos el código en un hilo independiente
        //puesto que es una conexión de red
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Inicializamos el socket, conectándonos a la IP
                    //del servidor y el puerto adecuado
                    Socket s = new Socket("172.27.0.41", 10000);
                    //Construimos el flujo más adecuado sobre
                    //el flujo de salida del socket
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
                    //Enviamos la información oportuna
                    printWriter.println("HOLAAAAAA!!!!");
                    printWriter.println("NOS HEMOS CONECTADO!!!!!");
                    //Enviamos el mensaje de FIN de comunicación
                    printWriter.println("FIN");
                    //Forzamos el envío
                    printWriter.flush();
                    //Cerramos el socket, lo que hace que se cierren
                    //los flujos definidos sobre él
                    s.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();





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
