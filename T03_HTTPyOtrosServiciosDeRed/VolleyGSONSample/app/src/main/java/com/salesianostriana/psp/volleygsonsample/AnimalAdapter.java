package com.salesianostriana.psp.volleygsonsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luismi on 04/11/2015.
 */
public class AnimalAdapter extends BaseAdapter {


    Context mContext;
    List<Animal> data;

    public AnimalAdapter(Context _mContext) {
        mContext = _mContext;

        data = new ArrayList<Animal>();

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //En lugar de
        requestQueue.add(new ProgressDialogGsonRequest<Animal[]>(MainActivity.animalsURL, Animal[].class, null, new Response.Listener<Animal[]>() {
            @Override
            public void onResponse(Animal[] response) {
                data = Arrays.asList(response);
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error en la descarga de datos", Toast.LENGTH_LONG).show();
                data = new ArrayList<Animal>();
            }
        }
        , mContext));

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = v = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);

        TextView txt_especie, txt_descripcion;
        ImageView img;


        txt_especie = (TextView) v.findViewById(R.id.txt_nombre);
        txt_descripcion = (TextView) v.findViewById(R.id.txt_desc);
        img = (ImageView) v.findViewById(R.id.imageView);

        Animal a = (Animal) getItem(position);

        Picasso.with(mContext)
                .load(a.getImagen())
                .placeholder(R.drawable.placeholder)
                .into(img);

        txt_especie.setText(a.getNombre());
        txt_descripcion.setText(a.getDescripcion());


        return v;
    }
}
