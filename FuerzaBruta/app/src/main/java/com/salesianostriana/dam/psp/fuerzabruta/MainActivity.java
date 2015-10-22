package com.salesianostriana.dam.psp.fuerzabruta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String password = "";
    TextView prueba;
    Button testear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prueba = (TextView) findViewById(R.id.editTextPass);
        testear = (Button) findViewById(R.id.btnTestear);

        testear.setOnClickListener(this);

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
    public void onClick(View v) {
        if (v.getId() == R.id.btnTestear){
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (!password.equals(prueba.getText().toString())){
                        password = UtilRandomString.getCadenaAlfanumAleatoria(1);
                    }
                    Log.i("Password","La contraseña es: "+password);
                }
            });
            executorService.shutdown();
        }
    }
}
