package salesianostriana.com.psp.volleyfirstsample;

import android.content.Context;

import com.android.volley.RequestQueue;

/**
 * Created by flopez on 19/11/2015.
 */
public class VolleySingleton {

    private Context mContext;
    private RequestQueue mRequestQueue;


    private static VolleySingleton ourInstance = new VolleySingleton();

    public static VolleySingleton getInstance() {
        return ourInstance;
    }



    private VolleySingleton(Context mContext) {
    }
}
