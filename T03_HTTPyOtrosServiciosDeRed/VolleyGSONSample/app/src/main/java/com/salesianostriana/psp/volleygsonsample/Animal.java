package com.salesianostriana.psp.volleygsonsample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Luismi on 04/11/2015.
 */
public class Animal {


    @SerializedName("especie")
    private String nombre;
    private String descripcion;
    private String imagen;

    public Animal() {
    }

    public Animal(String descripcion, String imagen, String nombre) {
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
