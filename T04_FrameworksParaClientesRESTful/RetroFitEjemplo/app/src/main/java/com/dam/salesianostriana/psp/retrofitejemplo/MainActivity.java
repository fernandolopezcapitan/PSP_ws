package com.dam.salesianostriana.psp.retrofitejemplo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//xq lo q estamos es conviertiendo un GSON
                .build();

        new RetrofitTask().execute();
    }

    private class RetrofitTask implements AsyncTask<Void,Void,List<Repo>>{

        @Override
        protected List<Repo> doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(List<Repo> repos) {
            super.onPostExecute(repos);
        }
    }
}
