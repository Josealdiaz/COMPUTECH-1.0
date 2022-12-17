package com.example.computech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicioSesionActivity extends AppCompatActivity {
    private EditText meditTextEmail;
    private EditText meditTextContraseña;
    private Button buttonIniciarSesion;

    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        meditTextEmail = findViewById(R.id.EditTextEmail);
        meditTextContraseña = findViewById(R.id.EditTextContraseña);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        meditTextEmail = findViewById(R.id.EditTextEmail);
        meditTextContraseña = findViewById(R.id.EditTextContraseña);
        buttonIniciarSesion = findViewById(R.id.ButtonIniciarSesion);


        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(meditTextEmail);
                checkField(meditTextEmail);
                Log.d("TAG", "onClick: " + meditTextEmail.getText().toString());

                if (valid) {
                    fAuth.signInWithEmailAndPassword(meditTextEmail.getText().toString(), meditTextContraseña.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(InicioSesionActivity.this, "Login Iniciado", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InicioSesionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void checkUserAccessLevel(String uid) {

        DocumentReference df = fStore.collection("Usuarios").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());

                if (documentSnapshot.getString("Administrador") != null) {
                    startActivity(new Intent(getApplicationContext(), InicioAdminActivity.class));
                    finish();
                }
                if (documentSnapshot.getString("Usuario") != null) {
                    startActivity(new Intent(getApplicationContext(), InicioUsuarioActivity.class));
                    finish();
                }
            }
        });
    }

    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference df = FirebaseFirestore.getInstance().collection
                    ("Usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("Administrador") != null) {
                        startActivity(new Intent(getApplicationContext(), InicioAdminActivity.class));
                        finish();
                    }
                    if (documentSnapshot.getString("Usuario") != null) {
                        startActivity(new Intent(getApplicationContext(), InicioUsuarioActivity.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), InicioSesionActivity.class));
                    finish();
                }
            });
        }
    }
}