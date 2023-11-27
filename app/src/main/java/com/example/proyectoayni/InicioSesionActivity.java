package com.example.proyectoayni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesionActivity extends AppCompatActivity {

    private Button botonRegistrar, botonIngresar;
    private EditText LoginEmail, LoginPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        mAuth = FirebaseAuth.getInstance();
        LoginEmail = findViewById(R.id.loginCorreo);
        LoginPassword = findViewById(R.id.loginContraseña);
        botonIngresar = findViewById(R.id.btnIngresoSesion);

        botonRegistrar = findViewById(R.id.btnRegistro);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioSesionActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        /*if(currentUser != null){
            reload();
        }*/
    }
    public void inicioSesion(View view){
        mAuth.signInWithEmailAndPassword(LoginEmail.getText().toString().trim(),LoginPassword.getText().toString().trim())
                .addOnCompleteListener(InicioSesionActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(InicioSesionActivity.this, "Ingresando...",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(InicioSesionActivity.this, MenuAyni.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(InicioSesionActivity.this, "Authentication failed. Se tiene que registrar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}