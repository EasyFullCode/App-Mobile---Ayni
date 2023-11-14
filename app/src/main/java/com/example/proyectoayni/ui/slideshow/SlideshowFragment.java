package com.example.proyectoayni.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.proyectoayni.R;
import com.example.proyectoayni.databinding.FragmentSlideshowBinding;
import com.squareup.picasso.Picasso;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d("SlideshowFragment", "onCreateView() se ha llamado");

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener los datos del nombre y la foto del usuario desde los argumentos
        /*Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name");
            String photoUrl = args.getString("photoUrl");
            // Agregar registros para verificar los datos
            Log.d("SlideshowFragment", "Nombre del usuario: " + name);
            Log.d("SlideshowFragment", "URL de la foto: " + photoUrl);

            // Actualizar el TextView con el nombre del usuario
            binding.nameTextView.setText("Nombre: " + name);


            // Cargar la foto de perfil usando Picasso
            ImageView profileImageView = binding.profileImageView;
            if (photoUrl != null) {
                Picasso.get().load(photoUrl).into(profileImageView);
            }
        }*/

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("SlideshowFragment", "onViewCreated() se ha llamado");
        Button botonIntercambio = view.findViewById(R.id.btnIntercambios);

        botonIntercambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_intercambios);
            }
        });
    }

    @Override
    public void onDestroyView() {
        Log.d("SlideshowFragment", "onDestroyView() se ha llamado");
        super.onDestroyView();
        binding = null;
    }
}