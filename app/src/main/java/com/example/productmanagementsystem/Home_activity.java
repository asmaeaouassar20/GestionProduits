package com.example.productmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Récupérer les boutons flottants
        FloatingActionButton btnSignIn = findViewById(R.id.btnSignIn);
        FloatingActionButton btnSignUp = findViewById(R.id.btnSignUp);

        // Ajouter des actions aux boutons
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lorsque le bouton Sign In est cliqué, démarrer LoginActivity
                Intent intent = new Intent(Home_activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lorsque le bouton Sign Up est cliqué, démarrer SignUpActivity
                Intent intent = new Intent(Home_activity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
