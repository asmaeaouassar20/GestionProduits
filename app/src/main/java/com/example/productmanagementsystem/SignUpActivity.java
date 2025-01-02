package com.example.productmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    // Déclarer l'objet dbHelper pour interagir avec la base de données
    ProductTable dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialiser dbHelper
        dbHelper = new ProductTable(this);

        // Trouver les champs de saisie
        EditText edtNom = findViewById(R.id.etLastName);
        EditText edtPrenom = findViewById(R.id.etFirstName);
        EditText edtEmail = findViewById(R.id.etEmail);
        EditText edtMotDePasse = findViewById(R.id.etPassword);

        // Action lorsque l'utilisateur clique sur "Créer un compte"
        findViewById(R.id.btnSignUp).setOnClickListener(v -> {
            String nom = edtNom.getText().toString().trim();
            String prenom = edtPrenom.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String motDePasse = edtMotDePasse.getText().toString().trim();

            // Vérifier que tous les champs sont remplis
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer un objet User et l'ajouter à la base de données
            User newUser = new User(nom, prenom, email, motDePasse);
            dbHelper.addUser(newUser);

            // Afficher un message de succès
            Toast.makeText(this, "Inscription réussie !", Toast.LENGTH_SHORT).show();

            // Rediriger vers l'écran de connexion ou autre activité
             Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
             startActivity(intent);
             finish(); // Fermer l'activité SignUpActivity
        });
    }
}
