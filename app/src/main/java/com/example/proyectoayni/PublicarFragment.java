package com.example.proyectoayni;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicarFragment extends Fragment {

    private ActivityResultLauncher<Intent> galleryLauncher;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnOpenGallery;
    private View fragmentView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PublicarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublicarFragment newInstance(String param1, String param2) {
        PublicarFragment fragment = new PublicarFragment();
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

        fragmentView = inflater.inflate(R.layout.fragment_publicar, container, false);

        btnOpenGallery = fragmentView.findViewById(R.id.btnOpenGallery);

        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    handleImageSelection(selectedImageUri);
                }
            }
        });

        return fragmentView;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }


    public void handleImageSelection(Uri selectedImageUri){
        // Obtén la referencia al ImageView en tu diseño
        ImageView imageView = fragmentView.findViewById(R.id.imageView); // Asegúrate de que el ID sea correcto

        // Asigna la imagen seleccionada al ImageView
        imageView.setImageURI(selectedImageUri);

        imageView.setVisibility(View.VISIBLE);

        btnOpenGallery.setVisibility(View.GONE);
    }
}
