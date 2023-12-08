package com.example.conectamovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditarUsuarioActivity extends AppCompatActivity {

    private EditText editNombre;
    private EditText editContraseñaActual;
    private EditText editNuevaContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        // Initialize EditTexts
        editNombre = findViewById(R.id.edit_nombre);
        editContraseñaActual = findViewById(R.id.edit_contraseña_actual);
        editNuevaContraseña = findViewById(R.id.edit_nueva_contraseña);

        // Get the button reference
        Button btnActualizar = findViewById(R.id.btn_actualizar);

        // Set click listener for the button
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user information
                String nombre = editNombre.getText().toString().trim();
                String contraseñaActual = editContraseñaActual.getText().toString().trim();
                String nuevaContraseña = editNuevaContraseña.getText().toString().trim();

                // Validate user input
                if (TextUtils.isEmpty(nombre)) {
                    Toast.makeText(EditarUsuarioActivity.this, "Ingrese su nombre", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(contraseñaActual)) {
                    Toast.makeText(EditarUsuarioActivity.this, "Ingrese su contraseña actual", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(nuevaContraseña)) {
                    Toast.makeText(EditarUsuarioActivity.this, "Ingrese su nueva contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    // Update user's information
                    updateUser(nombre, contraseñaActual, nuevaContraseña);
                }
            }
        });
    }

    private void updateUser(String nombre, String contraseñaActual, String nuevaContraseña) {
        // Implement the logic for updating the user's information using Firebase Database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Update the user's name
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("nombre", nombre);

        // Update the user's password
        if (!TextUtils.isEmpty(nuevaContraseña)) {
            user.updatePassword(nuevaContraseña).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Update the user's password in the database
                        userData.put("contraseña", nuevaContraseña);

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(user.getUid());
                        databaseReference.updateChildren(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditarUsuarioActivity.this, "Información actualizada exitosamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditarUsuarioActivity.this, MenuPrincipalActivity.class));
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(EditarUsuarioActivity.this, "Error al actualizar la contraseña: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // Update the user's name in the database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(user.getUid());
            databaseReference.updateChildren(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(EditarUsuarioActivity.this, "Información actualizada exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditarUsuarioActivity.this, MenuPrincipalActivity.class));
                    finish();
                }
            });
        }
    }
}
