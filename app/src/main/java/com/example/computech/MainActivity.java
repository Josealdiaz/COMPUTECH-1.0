package com.example.computech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mbuttonIrInicio;
    private Button mbuttonIniciarSesion;
    private TextView mTextViewRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbuttonIrInicio = findViewById(R.id.ButtonIrInicio);
        mbuttonIniciarSesion = findViewById(R.id.ButtonIniciarSesion);
        mTextViewRegistrarse = findViewById(R.id.textViewRegistrate);

        mbuttonIrInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InicioAdminActivity.class));
            }
        });
        mbuttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InicioSesionActivity.class));
            }
        });

        mTextViewRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrarUsuarioActivity.class));
            }
        });
    }
}