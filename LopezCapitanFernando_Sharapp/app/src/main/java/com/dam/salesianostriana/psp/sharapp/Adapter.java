package com.dam.salesianostriana.psp.sharapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by flopez on 11/02/2016.
 */
public class Adapter extends ArrayAdapter<Mensaje>{

    Context context;
    ArrayList<Mensaje> lista;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public Adapter(Context context, ArrayList<Mensaje> objects) {
        super(context, 0, objects);
        this.context = context;
        this.lista = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);

        String nick = preferences.getString("user",null);

        Mensaje user = lista.get(position);

        TextView fecha = (TextView) v.findViewById(R.id.textViewFechaRecibo);
        TextView usuario = (TextView) v.findViewById(R.id.textViewUsuario);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageViewRecibida);
        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.contenedor);

        if(user.getNombre().equals(nick)){
            relativeLayout.setGravity(Gravity.RIGHT);
        } else {
            relativeLayout.setGravity(Gravity.LEFT);
        }

        Date fecha1 = user.getFecha();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        fecha.setText(sdf.format(fecha1));
        usuario.setText(user.getNombre());
        imageView.setImageBitmap(user.getImagen());


        return v;
    }
}
