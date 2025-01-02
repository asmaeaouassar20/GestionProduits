package com.example.productmanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ProductTable extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME = "ProductDB.db";
    private static int DATABASE_VERSION = 2;

    // Requête SQL pour créer la table ProductTable
    private static String createProductTableQuery = "CREATE TABLE ProductTable (id INTEGER PRIMARY KEY, name TEXT, qty INTEGER, price INTEGER)";

    // Requête SQL pour créer la table User
    private static String createUserTableQuery = "CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, prenom TEXT, email TEXT, mot_de_passe TEXT)";

    public ProductTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            // Créer les tables
            sqLiteDatabase.execSQL(createProductTableQuery);
            sqLiteDatabase.execSQL(createUserTableQuery);
        } catch (Exception exp) {
            Toast.makeText(context, exp.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer les anciennes tables si elles existent
        db.execSQL("DROP TABLE IF EXISTS ProductTable");
        db.execSQL("DROP TABLE IF EXISTS User");

        // Recréer les tables
        onCreate(db);
    }

    // Méthodes pour ProductTable

    public void addProduct(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", p.getName());
        values.put("qty", p.getQty());
        values.put("price", p.getPrice());

        db.insert("ProductTable", null, values);
        db.close();
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM ProductTable", null);
    }

    public Cursor getProduct(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM ProductTable WHERE name='" + name + "'", null);
    }

    public boolean deleteProduct(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int rowsAffected = sqLiteDatabase.delete(
                "ProductTable",
                "id = ?",
                new String[]{String.valueOf(id)}
        );
        sqLiteDatabase.close();
        return rowsAffected > 0;
    }

    public boolean updateProduct(int id, String name, int qty, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE ProductTable SET name ='" + name + "', qty ='" + qty + "', price='" + price + "' WHERE id='" + id + "'");
        return true;
    }
    public Cursor getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM ProductTable WHERE id = ?", new String[]{String.valueOf(id)});
    }


    // Méthodes pour User

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nom", user.getNom());
        values.put("prenom", user.getPrenom());
        values.put("email", user.getEmail());
        values.put("mot_de_passe", user.getMotDePasse());

        db.insert("User", null, values);
        db.close();
    }


}
