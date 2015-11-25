package com.salesianostriana.psp.parsedotcomlogin;

import android.net.Uri;

import com.android.volley.Response;

/**
 * Created by Luismi on 21/11/2015.
 */
public class UserLoginRequest extends GsonRequest<User> {


    public UserLoginRequest(String username, String password, Response.Listener<User> listener, Response.ErrorListener errorListener) {
        super(LoginActivity.BASE_URL + "/1/login?username="+username+"&password="+password,
                User.class, listener, errorListener);
    }
}
