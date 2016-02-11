package com.dam.salesianostriana.psp.sharapp;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by flopez on 11/02/2016.
 */
public class Mensaje {

    private String nombre;
    private Date fecha;
    private Bitmap imagen;

    public Mensaje(){}

    public Mensaje(String nombre, Date fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public Mensaje(String nombre, Date fecha, Bitmap imagen) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
