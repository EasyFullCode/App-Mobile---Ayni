package com.example.proyectoayni;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoayni.databinding.ActivityMenuAyniBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MenuAyni extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuAyniBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuAyniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenuAyni.toolbar);
        binding.appBarMenuAyni.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                com.facebook.login.LoginManager.getInstance().logOut();
                startActivity(new Intent(MenuAyni.this, MainActivity.class));
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_intercambios, R.id.nav_perfil, R.id.nav_publicar,R.id.nav_categorias,R.id.nav_acerca)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_ayni);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ayni, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_ayni);
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
}