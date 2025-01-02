package com.example.productmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditProductActivity extends AppCompatActivity {

    private EditText etEditName, etEditQty, etEditPrice;
    private Button btnSaveEdit;
    private ProductTable productTable;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Initialiser les vues
        etEditName = findViewById(R.id.etEditName);
        etEditQty = findViewById(R.id.etEditQty);
        etEditPrice = findViewById(R.id.etEditPrice);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);


        productTable = new ProductTable(this);


        productId = getIntent().getIntExtra("productID", -1);

        if (productId != -1) {
            loadProductDetails(productId);
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
        }


        btnSaveEdit.setOnClickListener(v -> {
            saveProductDetails();
        });
    }

    private void loadProductDetails(int productId) {

        Cursor cursor = productTable.getProductById(productId);
        if (cursor != null && cursor.moveToFirst()) {

            int nameIndex = cursor.getColumnIndex("name");
            int qtyIndex = cursor.getColumnIndex("qty");
            int priceIndex = cursor.getColumnIndex("price");

            if (nameIndex >= 0 && qtyIndex >= 0 && priceIndex >= 0) {

                String name = cursor.getString(nameIndex);
                int qty = cursor.getInt(qtyIndex);
                int price = cursor.getInt(priceIndex);


                etEditName.setText(name);
                etEditQty.setText(String.valueOf(qty));
                etEditPrice.setText(String.valueOf(price));
            } else {
                Toast.makeText(this, "Necessary columns are missing in the database.", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProductDetails() {

        String newName = etEditName.getText().toString();
        String newQtyStr = etEditQty.getText().toString();
        String newPriceStr = etEditPrice.getText().toString();


        if (newName.isEmpty() || newQtyStr.isEmpty() || newPriceStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int newQty = Integer.parseInt(newQtyStr);
        int newPrice = Integer.parseInt(newPriceStr);


        boolean isUpdated = productTable.updateProduct(productId, newName, newQty, newPrice);
        if (isUpdated) {
            Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(EditProductActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error saving changes", Toast.LENGTH_SHORT).show();
        }
    }
}
