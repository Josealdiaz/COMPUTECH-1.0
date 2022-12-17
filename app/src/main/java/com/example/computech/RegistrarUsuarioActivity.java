package com.example.computech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    EditText EditTextNombreApellido;
    EditText EditTextEmail;
    EditText EditTextContraseña;
    EditText EditTextCelular;
    Button ButtonRegistrarUsuario;
    RadioButton UsuarioRadio;

    boolean Validar = true;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        EditTextNombreApellido = findViewById(R.id.registrarNombreApellido);
        EditTextEmail = findViewById(R.id.registrarEmail);
        EditTextContraseña = findViewById(R.id.registrarContraseña);
        EditTextCelular = findViewById(R.id.registrarCelular);

        UsuarioRadio = findViewById(R.id.RadioButtonUsuario);

        ButtonRegistrarUsuario = findViewById(R.id.buttonRegistrarUsuario);

        ButtonRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(EditTextNombreApellido);
                checkField(EditTextEmail);
                checkField(EditTextContraseña);
                checkField(EditTextCelular);


                if (!(UsuarioRadio.isChecked() || UsuarioRadio.isChecked())) {
                    Toast.makeText(RegistrarUsuarioActivity.this, "Seleccione Usuario", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Validar) {
                    fAuth.createUserWithEmailAndPassword(EditTextEmail.getText().toString(), EditTextContraseña.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(RegistrarUsuarioActivity.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Usuarios").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Nombre", EditTextNombreApellido.getText().toString());
                            userInfo.put("Email", EditTextEmail.getText().toString());
                            userInfo.put("Celular", EditTextCelular.getText().toString());


                            if (UsuarioRadio.isChecked()) {
                                userInfo.put("Usuario", "1");
                            }

                            df.set(userInfo);


                            if (UsuarioRadio.isChecked()) {
                                startActivity(new Intent(getApplicationContext(), InicioUsuarioActivity.class));
                                finish();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrarUsuarioActivity.this, "Usuario No Creado", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });
    }


    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            Validar = false;
        } else {
            Validar = true;
        }

        return Validar;
    }
}