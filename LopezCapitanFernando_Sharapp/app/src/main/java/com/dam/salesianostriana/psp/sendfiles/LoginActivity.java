package com.dam.salesianostriana.psp.sendfiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button registro;
    EditText usuario;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.et_nick);
        registro = (Button) findViewById(R.id.btn_registrar);

        preferences = getSharedPreferences("sharapp", this.MODE_PRIVATE);
        editor = preferences.edit();

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickUsuario = usuario.getText().toString();

                editor.putString("user", nickUsuario);
                editor.apply();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                LoginActivity.this.finish();
            }
        });
    }
}
