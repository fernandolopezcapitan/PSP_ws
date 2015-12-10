package com.dam.salesianostriana.psp.retrofitejemplo;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by flopez on 10/12/2015.
 */
public interface GitHubService {

    @GET("/users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    //Si hay mas peticiones se ponen aqui
}
