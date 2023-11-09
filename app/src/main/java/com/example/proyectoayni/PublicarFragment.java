package com.example.proyectoayni;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicarFragment extends Fragment {

    private ActivityResultLauncher<Intent> galleryLauncher;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnOpenGallery,botonPublicar;
    private View fragmentView;

    private Spinner spinnerEstado;

    private Spinner spinnerCategoria, spinnerEntrega, spinnerCambio ;

    private EditText InputTitulo, InputContacto;

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

        botonPublicar = fragmentView.findViewById(R.id.btnPublicar);

        InputTitulo = fragmentView.findViewById(R.id.edtTitle);

        InputContacto = fragmentView.findViewById(R.id.edtCelular);

        spinnerCategoria = fragmentView.findViewById(R.id.edtCategoriaProducto);

        spinnerEstado = fragmentView.findViewById(R.id.edtEstado);

        spinnerEntrega = fragmentView.findViewById(R.id.edtLugar);

        spinnerCambio = fragmentView.findViewById(R.id.edtPreferencia);

        ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(requireContext(), R.array.edtCategoria, android.R.layout.simple_list_item_1);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(requireContext(), R.array.edtEstado, android.R.layout.simple_list_item_1);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterEstado);

        ArrayAdapter<CharSequence> adapterEntrega = ArrayAdapter.createFromResource(requireContext(), R.array.edtEntrega, android.R.layout.simple_list_item_1);
        adapterEntrega.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEntrega.setAdapter(adapterEntrega);

        ArrayAdapter<CharSequence> adapterCambio = ArrayAdapter.createFromResource(requireContext(), R.array.edtPreferencia, android.R.layout.simple_list_item_1);
        adapterCambio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCambio.setAdapter(adapterCambio);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSeleccionada = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String estadoSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCambio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String PreferenciaCambioSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEntrega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String PreferenciaEntregaSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        botonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InputTitulo.getText().toString().trim().isEmpty()){
                    Toast.makeText(requireContext(), "Llenar el campo Titulo", Toast.LENGTH_SHORT).show();
                } else if (InputContacto.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Llenar el campo Contacto", Toast.LENGTH_SHORT).show();
                }else {
                    String tituloProducto = InputTitulo.getText().toString();
                    String categoria = spinnerCategoria.getSelectedItem().toString();
                    String entrega = spinnerEntrega.getSelectedItem().toString();
                    String contacto = InputContacto.getText().toString();
                    String estado = spinnerEstado.getSelectedItem().toString();
                    String cambio = spinnerCambio.getSelectedItem().toString();
                }
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbref = db.getReference(Producto.class.getSimpleName());
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Producto producto1 = new Producto();
                        producto1.setTitulo(InputTitulo.getText().toString());
                        producto1.setCategoria(spinnerCategoria.getSelectedItem().toString());
                        producto1.setPreferencia_entrega(spinnerEntrega.getSelectedItem().toString());
                        producto1.setContacto(InputContacto.getText().toString());
                        producto1.setEstado_producto(spinnerEstado.getSelectedItem().toString());
                        producto1.setPreferencia_cambio(spinnerCambio.getSelectedItem().toString());

                        dbref.push().setValue(producto1);
                        //Toast.makeText(requireContext(), "Producto Registrado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog.Builder a = new AlertDialog.Builder(requireContext());
                a.setCancelable(true);
                a.setIcon(R.drawable.check);
                a.setTitle("Producto Registrado");
                a.setPositiveButton("Aceptar", (dialog, which) -> {
                    Navigation.findNavController(fragmentView).navigate(R.id.nav_intercambios);
                });
                a.show();
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
