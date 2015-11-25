package com.salesianostriana.psp.volleygsonsample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luismi on 04/11/2015.
 */
public class AnimalRVAdapter extends RecyclerView.Adapter<AnimalRVAdapter.ViewHolder> {


    List<Animal> data = new ArrayList<Animal>();
    Context mContext;

    public AnimalRVAdapter(Context _mContext) {
        mContext = _mContext;

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        /*requestQueue.add(new StringRequest(Request.Method.GET, MainActivity.animalsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                data = Arrays.asList(gson.fromJson(response, Animal[].class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error en la descarga de datos", Toast.LENGTH_LONG).show();
                data = new ArrayList<Animal>();
            }
        }
        ));*/


        requestQueue.add(new GsonRequest<Animal[]>(MainActivity.animalsURL, Animal[].class, null, new Response.Listener<Animal[]>() {
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
        ));


        //data =
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_layout, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Animal a = data.get(i);


        Picasso.with(mContext)
                .load(a.getImagen())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.img);

        viewHolder.txt_especie.setText(a.getNombre());
        viewHolder.txt_descripcion.setText(a.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_especie, txt_descripcion;
        ImageView img;

        public ViewHolder(View v) {
            super(v);
            txt_especie = (TextView) v.findViewById(R.id.txt_nombre);
            txt_descripcion = (TextView) v.findViewById(R.id.txt_desc);
            img = (ImageView) v.findViewById(R.id.imageView);
        }
    }


}
