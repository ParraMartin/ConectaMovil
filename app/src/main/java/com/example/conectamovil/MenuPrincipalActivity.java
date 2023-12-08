package com.example.conectamovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuPrincipalActivity extends AppCompatActivity {

    private static final int IMAGE_PICKER_REQUEST_CODE = 1;
    private ImageView imageView;
    private Button btnedtSesion;
    private Button btnAgregar;
    private ImageButton btnCerrar;

    private FirebaseAuth firebaseAuth;
    private Button btnSelectProfilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        imageView = findViewById(R.id.imageView);
        btnedtSesion = findViewById(R.id.btnedtSesion);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnCerrar = findViewById(R.id.btnCerrar);
        btnSelectProfilePicture = findViewById(R.id.btnSelectProfilePicture);

        imageView.setVisibility(View.GONE); // Inicialmente, oculta el ImageView

        firebaseAuth = FirebaseAuth.getInstance();
        // Obtener usuario actual de Firebase
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // Cargar información del usuario desde la base de datos
            loadUserData(user);
        }

        btnSelectProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnedtSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modifyUserIntent = new Intent(MenuPrincipalActivity.this, EditarUsuarioActivity.class);
                startActivity(modifyUserIntent);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addContactIntent = new Intent(MenuPrincipalActivity.this, ContactosActivity  .class);
                startActivity(addContactIntent);
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            // Carga la imagen seleccionada con Glide
            Glide.with(this)
                    .load(selectedImageUri)
                    .circleCrop()  // Recorta la imagen en un círculo
                    .override(100, 100) // Ajusta el tamaño a 100 x 100 píxeles
                    .into(imageView);

            // Muestra la imagen inmediatamente
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private void loadUserData(FirebaseUser user) {
    }

    private void logout() {
    }
}