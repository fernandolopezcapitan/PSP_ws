package com.salesianostriana.psp.retrofitpostsample;

import com.salesianostriana.psp.retrofitpostsample.model.Note;
import com.salesianostriana.psp.retrofitpostsample.model.PublicWall;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Luismi on 09/12/2015.
 */
public interface PublicWallApi {


    @Headers({
            "X-Parse-Application-Id: Mx7zqSsER9LgHyLDbZzsTxVulL5Ada61tDM17LkG",
            "X-Parse-REST-API-Key: pKb5ZJz13UlW4LzVFq4XrY4XtJnopcX1cqujSe86"
    })
    @GET("/1/classes/PublicWall")
    Call<PublicWall> loadPublicWall();

    /*@Headers({
            "X-Parse-Application-Id: Mx7zqSsER9LgHyLDbZzsTxVulL5Ada61tDM17LkG",
            "X-Parse-REST-API-Key: pKb5ZJz13UlW4LzVFq4XrY4XtJnopcX1cqujSe86",
            "Content-Type: application/json"
    })*/

    //No hace falta poner los dos servicios X-Parse otra vez, usa los interceptores
    // Hacerlo de esta manera en el proyecto
    @Headers({"Content-Type: application/json"})
    @POST("/1/classes/PublicWall")
    Call<Note> createNote(@Body Note note);




}
