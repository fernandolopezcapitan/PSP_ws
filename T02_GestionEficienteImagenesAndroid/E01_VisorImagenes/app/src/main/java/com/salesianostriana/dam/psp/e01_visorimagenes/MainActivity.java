package com.salesianostriana.dam.psp.e01_visorimagenes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button ant, sig;
    ImageView img;
    int stacks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.imageView);
        ant = (Button) findViewById(R.id.btn_anterior);
        sig = (Button)findViewById(R.id.btn_siguiente);

        final ArrayList<Integer> fotos = new ArrayList<Integer>();
        fotos.add(R.drawable.alcazabaalmeria);
        fotos.add(R.drawable.alcazabamalaga);
        fotos.add(R.drawable.alhambra);
        fotos.add(R.drawable.catedraljaen);
        fotos.add(R.drawable.giralda);
        fotos.add(R.drawable.mezquitacordoba);
        fotos.add(R.drawable.monasteriorabida);
        fotos.add(R.drawable.salvadorubeda);
        fotos.add(R.drawable.torreoro);

        stacks = (int) (Math.random()*fotos.size());
        Picasso.with(MainActivity.this)
                .load(fotos.get(stacks))
                .resize(350,350)
                .into(img);

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stacks++;
                if (stacks > (fotos.size() - 1)) {
                    stacks = 0;
                }
                Picasso.with(MainActivity.this)
                        .load(fotos.get(stacks))
                        .resize(350, 350)
                        .into(img);
            }
        });


        ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stacks--;
                if (stacks < 0) {
                    stacks = fotos.size() - 1;
                }
                Picasso.with(MainActivity.this)
                        .load(fotos.get(stacks))
                        .resize(350, 350)
                        .into(img);
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
}
