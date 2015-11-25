package com.salesianostriana.psp.volleygsonsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


/**
 * Ejemplo concreto de implementación de una Custom Request, en particular,
 * la petición que procesa usando GSON y que google propone directamente en su documentación.
 * Además, se complementa con una extensión de esta clase que muestra, siempre que se use
 * dicha petición, un diálogo de progreso automáticamente.
 */
public class MainActivity extends AppCompatActivity {



    ListView list;
    RecyclerView recycler_list;
    List<Animal> data;
    int tipo = 2;
    final int LIST_VIEW = 1;
    final int RECYCLER_VIEW = 2;
    static final String animalsURL = "http://www.salesianos-triana.com/dam/gson/animales.json";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tipo = LIST_VIEW;

        switch (tipo) {
            case LIST_VIEW:
                setContentView(R.layout.activity_main_listview);
                list = (ListView) findViewById(R.id.listView);
                list.setAdapter(new AnimalAdapter(MainActivity.this));
                break;
            case RECYCLER_VIEW:
                setContentView(R.layout.activity_main_recyclerview);
                recycler_list = (RecyclerView) findViewById(R.id.my_recycler_view);
                recycler_list.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                recycler_list.setLayoutManager(mLayoutManager);
                recycler_list.setAdapter(new AnimalRVAdapter(MainActivity.this));
            default:
                break;
        }



        //new DownloadAnimalTask().execute();


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

/*
    private class DownloadAnimalTask extends AsyncTask<Void, Void, List<Animal>> {

        @Override
        protected List<Animal> doInBackground(Void... params) {

            URL url = null;
            BufferedReader br = null;
            List<Animal> result = null;
            try {
                url = new URL("http://www.salesianos-triana.com/dam/gson/animales.json");

                br = new BufferedReader(new InputStreamReader(url.openStream()));

                Gson gson = new Gson();
                result = Arrays.asList(gson.fromJson(br,Animal[].class));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(List<Animal> animals) {
            super.onPostExecute(animals);
            if (animals != null) {
                switch (tipo) {
                    case LIST_VIEW:
                        list.setAdapter(new AnimalAdapter(MainActivity.this));
                        break;
                    case RECYCLER_VIEW:
                        recycler_list.setAdapter(new AnimalRVAdapter(MainActivity.this));
                    default:

                }
                data = animals;
            }

        }
    }
*/


}
