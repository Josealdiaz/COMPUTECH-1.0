package com.example.computech.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.computech.InicioAdminActivity;
import com.example.computech.InicioUsuarioActivity;
import com.example.computech.MainActivity;
import com.example.computech.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CrearUsuarioFragment extends Fragment {

    private EditText mEditTextNombreApellido;
    private EditText mEditTextEmail;
    private EditText mEditTextContraseña;
    private EditText mEditTextCelular;
    private Button mButtonRegistrarUsuario;
    RadioButton mAdminitradorRadio, mUsuarioRadio;
    boolean Validar = true;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearUsuarioFragment() {
        // Required empty public constructor
    }

    public static CrearUsuarioFragment newInstance(String param1, String param2) {
        CrearUsuarioFragment fragment = new CrearUsuarioFragment();
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
        View vista = inflater.inflate(R.layout.fragment_crear_usuario, container, false);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mEditTextNombreApellido = vista.findViewById(R.id.registrarNombreApellido);
        mEditTextEmail = vista.findViewById(R.id.registrarEmail);
        mEditTextContraseña = vista.findViewById(R.id.registrarContraseña);
        mEditTextCelular = vista.findViewById(R.id.registrarCelular);

        mAdminitradorRadio = vista.findViewById(R.id.RadioButtonAdministrador);
        mUsuarioRadio = vista.findViewById(R.id.RadioButtonUsuario);

        mButtonRegistrarUsuario = vista.findViewById(R.id.buttonRegistrarUsuario);
        mButtonRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(mEditTextNombreApellido);
                checkField(mEditTextEmail);
                checkField(mEditTextContraseña);
                checkField(mEditTextCelular);

                if (!(mAdminitradorRadio.isChecked() || mUsuarioRadio.isChecked())) {
                    Toast.makeText(getContext(), "Seleccione el tipo de cuentan", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Validar) {
                    fAuth.createUserWithEmailAndPassword(mEditTextEmail.getText().toString(), mEditTextContraseña.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Usuario Creado", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Usuarios").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Nombre", mEditTextNombreApellido.getText().toString());
                            userInfo.put("Email", mEditTextEmail.getText().toString());
                            userInfo.put("Celular", mEditTextCelular.getText().toString());

                            if (mAdminitradorRadio.isChecked()) {
                                userInfo.put("Administrador", "1");
                            }
                            if (mUsuarioRadio.isChecked()) {
                                userInfo.put("Usuario", "1");
                            }

                            df.set(userInfo);

                            if (mAdminitradorRadio.isChecked()) {
                                startActivity(new Intent(getContext(), InicioAdminActivity.class));
                                //   finish();
                            }
                            if (mUsuarioRadio.isChecked()) {
                                startActivity(new Intent(getContext(), InicioUsuarioActivity.class));
                                //  finish();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Usuario No Creado", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });
        return vista;
    }


    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            Validar = false;
        }else {
            Validar = true;
        }

        return Validar;
    }

}
