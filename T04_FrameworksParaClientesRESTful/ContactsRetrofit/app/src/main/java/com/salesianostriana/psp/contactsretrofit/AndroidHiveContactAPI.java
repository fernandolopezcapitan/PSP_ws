package com.salesianostriana.psp.contactsretrofit;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Profesor on 30/11/2015.
 */
public interface AndroidHiveContactAPI {

    @GET("/contacts")
    Call<Contacts> listContacts();

}
