package com.salesianostriana.psp.volleyfirstsample;

import android.app.Application;
import android.app.DownloadManager;

import com.android.volley.RequestQueue;

/**
 * Created by Profesor on 19/11/2015.
 */
public class VolleyApplication extends Application{

    public static RequestQueue requestQueue;


    @Override
    public void onCreate() {
        super.onCreate();

        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

    }
}
