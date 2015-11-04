package com.salesianostriana.psp.picassotransformationreyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Luismi on 30/10/2015.
 */
public class PicassoTransformationAdapter
        extends RecyclerView.Adapter<PicassoTransformationAdapter.PicassoTransformationsViewHolder> {

    //Colección de transformaciones
    Transformation[] t;
    //Contexto, necesario para varias operaciones
    Context mContext;

    public PicassoTransformationAdapter(Transformation[] _t, Context context) {
        t = _t;
        mContext = context;
    }


    /*
        Este método nos sirve para implementar el patrón ViewHolder, y aligerar
        al teléfono con la reutilización efectiva de la interfaz de cada item
     */
    @Override
    public PicassoTransformationsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_layout, viewGroup, false);

        PicassoTransformationsViewHolder vh = new PicassoTransformationsViewHolder(v);

        return vh;
    }


    /*
        Este método es el que realmente pinta cada elemento de la "lista".
        Lo que hacemos en el es cargar la misma foto de siempre, pero aplicando
        el efecto correspondiente.
     */

    @Override
    public void onBindViewHolder(PicassoTransformationsViewHolder picassoTransformationsViewHolder, int i) {

        Picasso.with(mContext)
                .load(R.drawable.puentetriana)
                .transform((Transformation) t[i])
                .into(picassoTransformationsViewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return t.length;
    }


    /*
        Clase ViewHolder, que se usará en el adaptador.
     */
    public static class PicassoTransformationsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public PicassoTransformationsViewHolder(View  v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }


    }

}
