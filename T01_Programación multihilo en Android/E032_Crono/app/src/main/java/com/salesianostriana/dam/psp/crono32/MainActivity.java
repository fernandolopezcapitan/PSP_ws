package com.salesianostriana.dam.psp.crono32;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView final_cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos los elementos de la IU
        final EditText editTiempoTotal = (EditText) findViewById(R.id.editTiempoTotal);
        final Button btn_empezar = (Button) findViewById(R.id.btn_empezar);
        final TextView txtTiempoRestante = (TextView) findViewById(R.id.txtTiempoRestante);
        /*final TextView*/ final_cuenta = (TextView) findViewById(R.id.textView5);
        EditText milisegundos = (EditText) findViewById(R.id.editTextMilisegundos);
        Button btn_pausa = (Button) findViewById(R.id.btn_pausa);


        //Asignamos al botón un manejador del evento click.
        btn_empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        double tiempo = Double.parseDouble(String.valueOf(editTiempoTotal.getText()));
                        double frecuencia = Double.parseDouble(String.valueOf(editTiempoTotal.getText())) / 1000;
                        while (tiempo > 0){
                            tiempo = tiempo - frecuencia;
                            try {
                                Thread.sleep((long) (frecuencia * 1000));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (tiempo < 0)
                                tiempo = 0;
                            actualizarTiempo(txtTiempoRestante, tiempo);
                        }

//                        for (int i = tiempo; i != -1; i--) {
//                            //if(i == 0){
//                            //    final_cuenta.setText("Cuenta finalizada");
//                            //}
//                            actualizarTiempo(txtTiempoRestante, i);
//
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }
                });
                executorService.shutdown();
            }
        });
    }
    /*
        Este método sirve para actualizar el texto que aparece en pantalla
        y que refleja el número de segundos restantes que le quedan al cronómetro

        Hay que invocarlo pasandole como primer argumento la variable txtTiempoRestante
     */
    public void actualizarTiempo(final TextView txt, final double tiempoRestante) {
        txt.post(new Runnable() {
            @Override
            public void run() {
                txt.setText(Integer.toString((int) tiempoRestante) + " s.");
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
