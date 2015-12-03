package com.salesianostriana.psp.volleygsonsample;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;

import java.util.Map;

/**
 * Created by Luismi on 21/11/2015.
 */



/*
    Añadimos a la clase GsonRequest proporcionada por Google en su tutorial de Volley,
    la posibilidad de mostrar siempre que se realice una petición en la red un diálogo
    de progreso de estilo spinner.

    Inicializamos el diálogo en el constructor, y lo ocultamos en el método
    deliverResponse

 */

public class ProgressDialogGsonRequest<T> extends GsonRequest {

    Context mContext;
    ProgressDialog progDailog;


    public ProgressDialogGsonRequest(String url, Class clazz, Map headers, Response.Listener listener,
                                     Response.ErrorListener errorListener, Context _mContext) {
        super(url, clazz, headers, listener, errorListener);
        mContext = _mContext;

        progDailog = new ProgressDialog(mContext);
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();


    }

    @Override
    protected void deliverResponse(Object response) {
        progDailog.dismiss();
        super.deliverResponse(response);
    }
}
