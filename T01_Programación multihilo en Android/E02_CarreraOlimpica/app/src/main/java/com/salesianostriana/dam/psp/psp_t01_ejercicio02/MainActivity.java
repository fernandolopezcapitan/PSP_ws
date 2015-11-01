package com.salesianostriana.dam.psp.psp_t01_ejercicio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] atletas = {"Yoye", "Carlos", "Jesús", "Fernando", "Cristina", "Manuel", "Jota", "Fran"};
        List<Corredor> podium = new LinkedList<Corredor>();



        for (int i = 0 ; i < atletas.length; i++) {

           new Thread(new Corredor(atletas[i])).start();

            for (int i = 0; i <podium.size(); i++){
                Log.i("PODIO", Integer.toString(i+1)+ ". "+podium.get())
            }


        }
        ExecutorService executorService = Executors.newFixedThreadPool(atletas.length);

        for (Corredor c : atletas){
            executorService.execute(c);

        }

        executorService.shutdown();
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
