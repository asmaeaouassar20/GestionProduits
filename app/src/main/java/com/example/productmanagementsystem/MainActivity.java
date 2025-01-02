package com.example.productmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    EditText etID1, etName1, etPrice1;
    Button btnSave1, btnViewAll1, btnSearch1;
    FloatingActionButton btnEdit1, btnDelete1;
    EditText etQty1;
    ProductTable table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName1 = findViewById(R.id.etName);
        etID1 = findViewById(R.id.etID);
        etPrice1 = findViewById(R.id.etPrice);
        etQty1 = findViewById(R.id.etQty);
        btnSave1 = findViewById(R.id.btnSave);
        btnViewAll1 = findViewById(R.id.btnViewAll);
        btnSearch1 = findViewById(R.id.btnSearch);
        btnEdit1 = findViewById(R.id.btnEdit);
        btnDelete1 = findViewById(R.id.btnDelete);
        FloatingActionButton  btnLogout= findViewById(R.id.btnLogout);
        table = new ProductTable(this);
        Product product = new Product();
        btnLogout.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, Home_activity.class);
            startActivity(intent);

        });

        btnSave1.setOnClickListener(view -> {
            try {
                String name = etName1.getText().toString();
                int qty = Integer.parseInt(etQty1.getText().toString());
                int price = Integer.parseInt(etPrice1.getText().toString());

                product.setName(name);
                product.setPrice(price);
                product.setQty(qty);

                table.addProduct(product);
                Toast.makeText(MainActivity.this, "Product is saved", Toast.LENGTH_SHORT).show();
                etName1.setText("");
                etPrice1.setText("");
                etQty1.setText("");

            } catch (NumberFormatException exp) {
                Toast.makeText(MainActivity.this, "Error: Please enter correct data", Toast.LENGTH_SHORT).show();
            }
        });


        btnViewAll1.setOnClickListener(view -> ViewAllProducts());




        btnSearch1.setOnClickListener(view -> SearchProducts());




        btnEdit1.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(etID1.getText().toString());
                // Passer l'ID du produit à l'activité d'édition
                Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
                intent.putExtra("productID", id);
                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Error: Please enter a valid ID", Toast.LENGTH_SHORT).show();
            }
        });





        btnDelete1.setOnClickListener(view -> {

            try {

                int id = Integer.parseInt(etID1.getText().toString());
                boolean result = table.deleteProduct(id);

                if (result) {
                    Toast.makeText(MainActivity.this, "Record is Deleted", Toast.LENGTH_SHORT).show();
                    etID1.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "Error: Please enter correct ID", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException exp) {
                Toast.makeText(MainActivity.this, "Error: Please enter correct ID", Toast.LENGTH_SHORT).show();
            }


        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_help) {
            showHelpDialog();
            return true;
        } else if (itemId == R.id.menu_logout) {
            showLogoutDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    // Afficher le dialogue d'aide
    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Help")
                .setMessage("1. Save: Saves the product information entered.\n" +
                        "2. Product List: Displays a list of all saved products.\n" +
                        "3. Search: Allows you to search for a product by name.\n" +
                        "4. Edit: Allows you to edit the product details.\n" +
                        "5. Delete: Allows you to delete a product by ID.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    // Afficher le dialogue de déconnexion
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Code pour la déconnexion, par exemple, retour à l'écran de connexion
                    Toast.makeText(MainActivity.this, "You are logged out", Toast.LENGTH_SHORT).show();
                    // Redirection vers une activité de connexion si nécessaire
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Ferme l'activité actuelle
                })
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss())
                .create()
                .show();
    }

    // Méthode pour afficher un message dans un dialogue
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void ViewAllProducts() {
        Cursor res = table.getAllProducts();
        if (res.getCount() == 0) {
            showMessage("Error", "Nothing found");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("Id: ").append(res.getString(0)).append("\n");
            buffer.append("Product Name: ").append(res.getString(1)).append("\n");
            buffer.append("Price: ").append(res.getString(2)).append("\n");
            buffer.append("Quantity: ").append(res.getString(3)).append("\n");
        }
        showMessage("Our Products", buffer.toString());
    }

    private void SearchProducts() {
        String name = etName1.getText().toString();
        Cursor res = table.getProduct(name);
        if (res.getCount() == 0) {
            showMessage("Error", "Please enter the product name");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("Id: ").append(res.getString(0)).append("\n");
            buffer.append("Product Name: ").append(res.getString(1)).append("\n");
            buffer.append("Price: ").append(res.getString(2)).append("\n");
            buffer.append("Quantity: ").append(res.getString(3)).append("\n");
        }
        showMessage("Product Information", buffer.toString());
        etName1.setText("");
    }



}
