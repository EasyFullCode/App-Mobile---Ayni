package com.example.proyectoayni;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectoayni.databinding.FragmentChaquetaBinding;
import com.example.proyectoayni.databinding.FragmentChicagoBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChicagoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChicagoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentChicagoBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChicagoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChicagoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChicagoFragment newInstance(String param1, String param2) {
        ChicagoFragment fragment = new ChicagoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChicagoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button whatsappButton = root.findViewById(R.id.whatsappButton);
        whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Número de teléfono al que se enviará el mensaje por WhatsApp
                String phoneNumber = "931561797";

                // Mensaje opcional a enviar
                String message = "Hola, vi este articulo en Ayni, me gustaria más información...";

                try {
                    // Crear la intención para abrir WhatsApp
                    String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    // Manejar excepciones si WhatsApp no está instalado en el dispositivo
                    Toast.makeText(getContext(), "WhatsApp no está instalado en este dispositivo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button botonVolver = view.findViewById(R.id.btnVolver);

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ropaHombreFragment);
            }
        });
    }
}