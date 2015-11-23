package com.salesianostriana.dam.psp.e01_speedyweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    EditText text;
    Button cargar;
    WebView visorWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.et_url);
        cargar = (Button) findViewById(R.id.btn_cargarWeb);
        visorWeb = (WebView) findViewById(R.id.webViewVista);


        //final String url ="http://www.google.com";

        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = text.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                visorWeb.getSettings().setJavaScriptEnabled(true);
                                visorWeb.getSettings().setBuiltInZoomControls(true);

                                // Forzamos el webview para que abra los enlaces externos en el navegador
                                visorWeb.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        return false;
                                    }
                                });

                                visorWeb.loadDataWithBaseURL(null, response, "text/html", "utf-8", null);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR URL",error.getMessage());
                    }
                });
                VolleyApplication.requestQueue.add(stringRequest);
            }
        });
    }
}
