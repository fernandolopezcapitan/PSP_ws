package com.dam.salesianostriana.psp.servidor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //es una conexión de red por lo que lo ejecutamos en un hilo secundario
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // Inicializamos el socket, conectandonos a la IP del servidor y del puerto adecuado
                    Socket s = new Socket("172.27.0.41",10000);
                    //Socket s = new Socket("172.27.60.8",10000);

                    // Construimos el flujo más adecuado sobre el flujo de salida del socket
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

                    // enviamos la info
                    printWriter.println("Hola");
                    printWriter.println("Estamos conectados, soy Fernando");

                    // enviamos el mensaje FIN de comunicación ( en este caso "FIN")
                    printWriter.println("FIN");

                    // forzamos el envío
                    printWriter.flush();

                    // cerramos el socket y con ello lo socket definidos con él
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
