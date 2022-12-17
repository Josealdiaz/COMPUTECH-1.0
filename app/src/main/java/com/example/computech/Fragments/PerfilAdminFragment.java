package com.example.computech.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.computech.InicioSesionActivity;
import com.example.computech.R;
import com.google.firebase.auth.FirebaseAuth;


public class PerfilAdminFragment extends Fragment {


    private Button mbuttonRegistrarUsuario;
    private Button mbuttonEditarUsuario;
    private TextView mtextViewTituloUsuario;
    private TextView mtextViewnombre;
    private TextView mtextViewCorreo;
    private TextView mtextViewTelefono;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilAdminFragment newInstance(String param1, String param2) {
        PerfilAdminFragment fragment = new PerfilAdminFragment();
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
        View vista = inflater.inflate(R.layout.fragment_perfil_admin, container, false);

        mbuttonRegistrarUsuario = vista.findViewById(R.id.buttonRegistrarUsuario);
        mbuttonEditarUsuario = vista.findViewById(R.id.buttonEditarUsuario);

        mtextViewTituloUsuario = vista.findViewById(R.id.textViewTituloUsuario);
        mtextViewnombre = vista.findViewById(R.id.textViewnombre);
        mtextViewCorreo = vista.findViewById(R.id.textViewCorreo);
        mtextViewTelefono = vista.findViewById(R.id.textViewTelefono);

        Button logout = vista.findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),InicioSesionActivity.class));
                //finish();
            }
        });


                mbuttonRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setReorderingAllowed(true);

                        fragmentTransaction.replace(R.id.LinearLayoutRemplazarUsuario, CrearUsuarioFragment.newInstance(" ", " "));
                        fragmentTransaction.commit();

                        mbuttonEditarUsuario.setVisibility(View.GONE);
                        mbuttonRegistrarUsuario.setVisibility(View.GONE);
                        mtextViewTituloUsuario.setVisibility(View.GONE);
                        mtextViewnombre.setVisibility(View.GONE);
                        mtextViewCorreo.setVisibility(View.GONE);
                        mtextViewTelefono.setVisibility(View.GONE);
                    }
                });

                mbuttonEditarUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setReorderingAllowed(true);

                        fragmentTransaction.replace(R.id.LinearLayoutRemplazarUsuario, Usuario_EditarUsuarioFragment.newInstance(" ", " "));
                        fragmentTransaction.commit();

                        mbuttonEditarUsuario.setVisibility(View.GONE);
                        mbuttonRegistrarUsuario.setVisibility(View.GONE);
                        mtextViewTituloUsuario.setVisibility(View.GONE);
                        mtextViewnombre.setVisibility(View.GONE);
                        mtextViewCorreo.setVisibility(View.GONE);


                    }
                });

                return vista;
            }

}