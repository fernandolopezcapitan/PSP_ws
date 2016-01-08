package com.salesianostriana.psp.retrofitpostsample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.salesianostriana.psp.retrofitpostsample.model.Note;
import com.salesianostriana.psp.retrofitpostsample.model.PublicWall;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText title, body;
    Button button;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        title = (EditText) findViewById(R.id.title);
        body = (EditText) findViewById(R.id.body);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().equalsIgnoreCase("") || body.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Rellene completamente el formulario, por favor", Toast.LENGTH_LONG).show();
                } else {
                    Note n = new Note();
                    n.setTitle(title.getText().toString());
                    n.setBody(body.getText().toString());

                    Call<Note> call = makeServiceWithInterceptors().createNote(n);

                    call.enqueue(new Callback<Note>() {
                        @Override
                        public void onResponse(Response<Note> response, Retrofit retrofit) {
                            loadData();
                            resetForm();
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(MainActivity.this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        loadData();


    }

    private void resetForm() {
        title.setText("");
        body.setText("");
    }


    private PublicWallApi makeService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.parse.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        PublicWallApi service = retrofit.create(PublicWallApi.class);

        return service;
    }

    private PublicWallApi makeServiceWithInterceptors() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {

                //Aqui es donde añade las cabeceras anteriores para poder acceder al servicio
                Request newRequest = chain.request().newBuilder()
                        .addHeader("X-Parse-Application-Id", "Mx7zqSsER9LgHyLDbZzsTxVulL5Ada61tDM17LkG")
                        .addHeader("X-Parse-REST-API-Key","pKb5ZJz13UlW4LzVFq4XrY4XtJnopcX1cqujSe86")
                        .build();

                return chain.proceed(newRequest);
            }
        };

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.parse.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        PublicWallApi service = retrofit.create(PublicWallApi.class);

        return service;
    }


    private void loadData() {


        Call<PublicWall> publicWallCall = makeService().loadPublicWall();

        publicWallCall.enqueue(new Callback<PublicWall>() {
            @Override
            public void onResponse(Response<PublicWall> response, Retrofit retrofit) {

                PublicWall result = response.body();

                listView.setAdapter(new PublicWallAdapter(MainActivity.this, result));

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }


    private class PublicWallAdapter extends BaseAdapter  {


        Context mContext;
        PublicWall data;


        public PublicWallAdapter(Context mContext, PublicWall data) {
            this.data = data;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return data.getNotes().size();
        }

        @Override
        public Object getItem(int position) {
            return data.getNotes().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2, parent, false);

            TextView title = (TextView) row.findViewById(android.R.id.text1);
            TextView body  = (TextView) row.findViewById(android.R.id.text2);

            Note item = (Note) getItem(position);

            title.setText(item.getTitle());
            body.setText(item.getBody());

            return row;
        }
    }


}
