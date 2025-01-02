package com.example.productmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    ProductTable dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new ProductTable(this);

        EditText edtEmail = findViewById(R.id.etEmail);
        EditText edtPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnSignIn).setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isUserValid(email, password)) {
               Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            } else {
                // Lancer une boîte de dialogue pour proposer une inscription
                 showSignUpDialog();


            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(v -> {
            // Rediriger l'utilisateur vers l'activité SignUpActivity
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }

    private boolean isUserValid(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Requête SQL pour vérifier si l'email et le mot de passe existent
            cursor = db.rawQuery(
                    "SELECT * FROM User WHERE email = ? AND mot_de_passe = ?",
                    new String[]{email, password}
            );

            // Si un utilisateur est trouvé (count > 0), l'utilisateur est valide
            return cursor.getCount() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // En cas d'erreur, on retourne false
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();  // S'assurer que le cursor est toujours fermé
            }
        }
    }


    private void showSignUpDialog() {
        // Utiliser runOnUiThread pour éviter les plantages potentiels liés au thread
        runOnUiThread(() -> {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Erreur d'authentification");
                builder.setMessage("Nom d'utilisateur ou mot de passe incorrect. Voulez-vous créer un compte ?");

                builder.setPositiveButton("Oui", (dialog, which) -> {
                    // Rediriger vers l'activité SignUpActivity
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    dialog.dismiss(); // Fermer le dialogue
                });

                builder.setNegativeButton("Non", (dialog, which) -> {
                    // Fermer le dialogue sans autre action
                    dialog.dismiss();
                });


                AlertDialog dialog = builder.create();
                dialog.show();
            } catch (Exception e) {
                Log.e("LoginActivity", "Erreur lors de l'affichage du dialogue", e);
                Toast.makeText(LoginActivity.this, "Une erreur est survenue. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
