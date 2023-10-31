package com.example.proyectoayni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BienvenidoActivity extends AppCompatActivity {

        private Button botonBienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        botonBienvenido = findViewById(R.id.btnWelcome);

        botonBienvenido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidoActivity.this, InicioSesionActivity.class);
                startActivity(intent);
            }
        });
    }
}