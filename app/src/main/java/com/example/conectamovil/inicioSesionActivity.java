package com.example.conectamovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class inicioSesionActivity extends AppCompatActivity {

    private EditText editCorreos;
    private EditText editPass;
    private Button btnIniciosesion;
    private Button btnRegistrarsee;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        editCorreos = findViewById(R.id.editCorreos);
        editPass = findViewById(R.id.editPass);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegistrarsee = findViewById(R.id.btnRegistrarsee);
        btnRegistrarsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inicioSesionActivity.this, RegistroActivity.class));
            }
        });
        btnIniciosesion = findViewById(R.id.btnIniciosesion);
        btnIniciosesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para el inicio de sesión
                String correos = editCorreos.getText().toString();
                String pass = editPass.getText().toString();

                if (correos.isEmpty() || pass.isEmpty()) {
                    // Aquí puedes manejar el caso en que los campos estén vacíos
                    Toast.makeText(inicioSesionActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {

                    // Validación de correo electrónico
                    if (!Patterns.EMAIL_ADDRESS.matcher(correos).matches()) {
                        // Correo electrónico no válido
                        Toast.makeText(inicioSesionActivity.this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Validación de contraseña
                    if (pass.length() < 6) {
                        // Contraseña demasiado corta
                        Toast.makeText(inicioSesionActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Inicia sesión con Firebase Authentication
                    firebaseAuth.signInWithEmailAndPassword(correos, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // Inicio de sesión exitoso
                                    Toast.makeText(inicioSesionActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                    // Redirecciona a la actividad principal o realiza cualquier otra acción necesaria
                                    // Ejemplo:
                                    startActivity(new Intent(inicioSesionActivity.this, MenuPrincipalActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error en el inicio de sesión
                                    Toast.makeText(inicioSesionActivity.this, "Error en el inicio de sesión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
    }
}