package com.example.proyectoayni;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntercambiosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntercambiosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View fragmentView;

    private ListView listauno, listados;

    public IntercambiosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntercambiosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntercambiosFragment newInstance(String param1, String param2) {
        IntercambiosFragment fragment = new IntercambiosFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button botonPublicar = view.findViewById(R.id.btnIrPublicar);

        botonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_publicar);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_intercambios,container,false);

        listauno = fragmentView.findViewById(R.id.lstOne);
        listados = fragmentView.findViewById(R.id.lstTwo);



        listarProducto();
        listarEstado();

        return fragmentView;
    }


    public void listarProducto(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(Producto.class.getSimpleName());

        ArrayList<Producto> listaProducto = new ArrayList<>();
        ArrayAdapter<Producto> productoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaProducto);
        listauno.setAdapter(productoAdapter);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaProducto.clear(); // Limpia la lista antes de agregar los nuevos títulos
                for (DataSnapshot productoSnapshot : dataSnapshot.getChildren()) {
                    Producto producto = productoSnapshot.getValue(Producto.class);
                    if (producto != null) {
                        listaProducto.add(producto);
                    }
                }
                productoAdapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Maneja los errores si es necesario
            }
        });


    }

    public void listarEstado(){

        ArrayAdapter<CharSequence> listaEstado = ArrayAdapter.createFromResource(requireContext(),R.array.estadoProducto, android.R.layout.simple_list_item_1);
        listados.setAdapter(listaEstado);


    }
}