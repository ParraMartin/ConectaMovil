package com.example.conectamovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ContactosActivity extends AppCompatActivity {
    private EditText editNombre;
    private EditText editCorreo;
    private Button btnAgregar;
    FirebaseAuth firebaseAuth;

    // Add a zero-argument constructor
    public ContactosActivity() {
    }

    public ContactosActivity(String nombre, String correo) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        // Initialize edit text fields and button
        editNombre = findViewById(R.id.edit_nombre);
        editCorreo = findViewById(R.id.edit_correo);
        btnAgregar = findViewById(R.id.btn_agregar);

        // Set click listener for the agregar button
        btnAgregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String nombre = editNombre.getText().toString().trim();
                String correo = editCorreo.getText().toString().trim();

                if (!nombre.isEmpty() && !correo.isEmpty()) {
                    // Create a new Contacto object with the entered data
                    ContactosActivity newContact = new ContactosActivity(nombre, correo);

                    // Add the new contact to Firebase Database
                    DatabaseReference contactListRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contactos");
                    contactListRef.push().setValue(newContact);

                    // Clear edit text fields
                    editNombre.setText("");
                    editCorreo.setText("");

                    // Show success message
                    Toast.makeText(ContactosActivity.this, "Contacto agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ContactosActivity.this, "Por favor, ingrese el nombre y el correo electrónico del contacto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
