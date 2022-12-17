package com.example.computech.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computech.R;

import java.util.zip.Inflater;

public class UsuarioFragment extends Fragment {

    private TextView mtextViewTituloUsuario;
    private Button mbuttonPerfil;
    private Button mbuttonProductos;
    private Button mbuttonDireccion;
    private Button mbuttonMetodoPago;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    public static UsuarioFragment newInstance(String param1, String param2) {
        UsuarioFragment fragment = new UsuarioFragment();
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
        View vista = inflater.inflate(R.layout.fragment_usuario, container, false);

        mtextViewTituloUsuario = vista.findViewById(R.id.textViewTituloUsuario);
        mbuttonPerfil = vista.findViewById(R.id.buttonPerfil);
        mbuttonProductos = vista.findViewById(R.id.buttonProductos);
        mbuttonDireccion = vista.findViewById(R.id.buttonDireccion);
        mbuttonMetodoPago = vista.findViewById(R.id.buttonMetodoPago);

        mbuttonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);

                fragmentTransaction.replace(R.id.LinearLayoutRemplazarPerfil, PerfilAdminFragment.newInstance(" "," "));
                fragmentTransaction.commit();

                mtextViewTituloUsuario.setVisibility(View.GONE);
                mbuttonPerfil.setVisibility(View.GONE);
                mbuttonProductos.setVisibility(View.GONE);
                mbuttonDireccion.setVisibility(View.GONE);
                mbuttonMetodoPago.setVisibility(View.GONE);
            }
        });

         return vista;
        }
}