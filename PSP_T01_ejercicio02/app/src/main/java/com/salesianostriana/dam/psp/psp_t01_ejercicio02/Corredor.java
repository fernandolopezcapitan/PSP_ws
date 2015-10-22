package com.salesianostriana.dam.psp.psp_t01_ejercicio02;

import java.util.List;
import java.util.Random;

/**
 * Created by flopez on 04/10/2015.
 * Tema 01: Ejercicio 02
 */
public class Corredor implements Runnable{

    private String nombre = null;
    private List<Corredor> podium;

    public Corredor(String name, ){

        nombre = name;

    }

    @Override

    public void run() {
        Random r = new Random();
        for (int i = 0; i < 1500; i++)
            try {
                Thread.sleep(r.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("Ha cruzado la meta: "+nombre);

        podium.add(this);
    }

}
