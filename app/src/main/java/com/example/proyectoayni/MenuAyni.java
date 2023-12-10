package com.example.proyectoayni;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoayni.databinding.ActivityMenuAyniBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuAyni extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private static final int PICK_IMAGE_REQUEST = 1;
    private TextToSpeech textToSpeech;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuAyniBinding binding;
    private NavController navController;
    private GoogleSignInClient mGoogleSignInClient;
    private ChatbotDialog chatbotDialog;

    private boolean textToSpeechStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMenuAyniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mGoogleSignInClient = buildGoogleSignInClient();

        if (currentUser == null) {
            // El usuario no está autenticado, realiza la acción correspondiente
            // (por ejemplo, redirige a la pantalla de inicio de sesión).
            Intent intent = new Intent(MenuAyni.this, MainActivity.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual para evitar que el usuario retroceda a la pantalla principal
        }

        setSupportActionBar(binding.appBarMenuAyni.toolbar);
        textToSpeech = new TextToSpeech(this, this);
        binding.appBarMenuAyni.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if (mGoogleSignInClient != null) {
                    mGoogleSignInClient.signOut().addOnCompleteListener(MenuAyni.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Acciones después de cerrar sesión en Google
                            if (task.isSuccessful()) {
                                // La sesión de Google se cerró con éxito
                                // Puedes redirigir al usuario a la pantalla de inicio de sesión o realizar otras acciones necesarias.
                                startActivity(new Intent(MenuAyni.this, MainActivity.class));
                            } else {
                                // Error al cerrar sesión en Google
                                Toast.makeText(MenuAyni.this, "Error al cerrar sesión en Google", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // mGoogleSignInClient es nulo, maneja la situación según tus necesidades
                    // Puedes mostrar un mensaje de error o realizar otras acciones necesarias.
                    Toast.makeText(MenuAyni.this, "Error: mGoogleSignInClient es nulo", Toast.LENGTH_SHORT).show();
                }
                com.facebook.login.LoginManager.getInstance().logOut();
                startActivity(new Intent(MenuAyni.this, MainActivity.class));
            }
        });
        binding.appBarMenuAyni.fab2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showChatbotDialog();
                if (!textToSpeechStarted && textToSpeech != null) {
                    textToSpeech.speak("¡Hola!, soy el chatbot de Ayni, ¿En qué puedo ayudarte?", TextToSpeech.QUEUE_FLUSH, null, null);
                    textToSpeechStarted = true;
                }
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_ayni);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_intercambios, R.id.nav_perfil, R.id.nav_publicar,R.id.nav_categorias,R.id.nav_acerca)
                .setOpenableLayout(drawer)
                .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_ayni);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        return GoogleSignIn.getClient(this, gso);
    }
    private void showChatbotDialog() {
        if (chatbotDialog == null) {
            chatbotDialog = new ChatbotDialog(MenuAyni.this, navController);
        }

        chatbotDialog.show(); // Utiliza la instancia existente
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ayni, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_ayni);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, PublicarFragment.newInstance(null, null))
                        .addToBackStack(null) // Opcional: agrega a la pila de retroceso
                        .commit();
                PublicarFragment publicarFragment = (PublicarFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (publicarFragment != null) {
                    publicarFragment.handleImageSelection(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onInit(int status) {
       /* if (status == TextToSpeech.SUCCESS) {
            // Puedes usar textToSpeech para reproducir mensajes de voz
            textToSpeech.speak("Bienvenido a la aplicación AYNI", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            // Manejar errores de inicialización
            Toast.makeText(this, "Error al inicializar TextToSpeech", Toast.LENGTH_SHORT).show();
        }*/
    }
    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.signOut();
        }
        super.onDestroy();
    }
    private void mostrarChatbotDialog() {
        if (chatbotDialog == null) {
            chatbotDialog = new ChatbotDialog(this, navController);
        }

        chatbotDialog.showDialog();
    }

    private void ocultarChatbotDialog() {
        if (chatbotDialog != null) {
            chatbotDialog.hideDialog();
        }
    }
}