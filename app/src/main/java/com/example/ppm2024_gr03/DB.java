package com.example.ppm2024_gr03;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;

import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

    public static final String DBNAME = "users.db";
    private static final int DATABASE_VERSION = 2; // Incremented version

    public DB(@Nullable Context context) {
        super(context, DBNAME, null, DATABASE_VERSION); // Updated version here
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(email TEXT PRIMARY KEY, password TEXT, name TEXT, surname TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE adminUser(email TEXT PRIMARY KEY, password TEXT, name TEXT, surname TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT)");
        
        //Tabela per items
        db.execSQL("CREATE TABLE items (id INTEGER PRIMARY KEY AUTOINCREMENT,emri TEXT NOT NULL,cmimi REAL NOT NULL,pershkrimi TEXT,perbersit TEXT);");

        // Table for messages
        db.execSQL("CREATE TABLE messages(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, message TEXT)");

        // Insert default admin user
        String adminEmail = "admin@example.com";
        String adminPassword = BCrypt.hashpw("Admin@123", BCrypt.gensalt());
        String adminName = "Admin";
        String adminSurname = "User";
        String adminPhone = "1234567890";

        db.execSQL("INSERT INTO adminUser (email, password, name, surname, phone) VALUES (?, ?, ?, ?, ?)",
                new Object[]{adminEmail, adminPassword, adminName, adminSurname, adminPhone});
        insertDefaultItems(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist (and recreate them)
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS adminUser");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        db.execSQL("DROP TABLE IF EXISTS messages");
        db.execSQL("DROP TABLE IF EXISTS items");

        // Recreate tables
        onCreate(db);
    }

    // Check if email is associated with admin
    public Boolean checkAdminEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM adminUser WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public long insertMessage(String name, String email, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("message", message);

        return db.insert("messages", null, contentValues);
    }

    public List<UserMessage> getAllMessages() {
        List<UserMessage> messageList = new ArrayList<>();
        SQLiteDatabase readableDb = this.getReadableDatabase();

        Cursor cursor = readableDb.rawQuery("SELECT name, email, message FROM messages", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String email = cursor.getString(1);
                String message = cursor.getString(2);
                messageList.add(new UserMessage(name, email, message));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return messageList;
    }

    // Get the user's name (either regular user or admin)
    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Check the 'users' table
        Cursor userCursor = db.rawQuery("SELECT name FROM users WHERE email = ?", new String[]{email});
        if (userCursor.moveToFirst()) {
            String name = userCursor.getString(0);
            userCursor.close();
            return name;
        }
        userCursor.close();

        // If not found in 'users', check the 'adminUser' table
        Cursor adminCursor = db.rawQuery("SELECT name FROM adminUser WHERE email = ?", new String[]{email});
        if (adminCursor.moveToFirst()) {
            String name = adminCursor.getString(0);
            adminCursor.close();
            return name;
        }
        adminCursor.close();

        return null;
    }

    public Boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Check the `users` table
        Cursor userCursor = db.rawQuery("SELECT password FROM users WHERE email=?", new String[]{email});
        if (userCursor.moveToFirst()) {
            String storedHashedPassword = userCursor.getString(0);
            userCursor.close();

            if (BCrypt.checkpw(password, storedHashedPassword)) {
                return true;
            }
        }
        userCursor.close();

        // Check the `adminUser` table
        Cursor adminCursor = db.rawQuery("SELECT password FROM adminUser WHERE email=?", new String[]{email});
        if (adminCursor.moveToFirst()) {
            String storedHashedPassword = adminCursor.getString(0);
            adminCursor.close();

            if (BCrypt.checkpw(password, storedHashedPassword)) {
                return true;
            }
        }
        adminCursor.close();

        return false;
    }

    // Insert admin user
    public Boolean insertAdminUser(String email, String password, String name, String surname, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Check that no field is null
        if (email == null || password == null || name == null || surname == null || phone == null) {
            return false;
        }

        // Generate hashed password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        contentValues.put("email", email);
        contentValues.put("password", hashedPassword);
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("phone", phone);

        long result = db.insert("users", null, contentValues);

        return result != -1;
    }
    class Item {
        private final int id;
        private final String emri;
        private final double cmimi;
        private final String pershkrimi;
        private final String perbersit;

        public Item(int id, String emri, double cmimi, String pershkrimi, String perbersit) {
            this.id = id;
            this.emri = emri;
            this.cmimi = cmimi;
            this.pershkrimi = pershkrimi;
            this.perbersit = perbersit;
        }

        public int getId() {
            return id;
        }

        public String getEmri() {
            return emri;
        }

        public double getCmimi() {
            return cmimi;
        }

        public String getPershkrimi() {
            return pershkrimi;
        }

        public String getPerbersit() {
            return perbersit;
        }
    }
    private void insertDefaultItems(SQLiteDatabase db) {
        String[] items = {
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Iced Coffee', 3.5, 'Cold coffee with ice', 'Coffee, Ice, Sugar')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Iced Matcha', 4.0, 'Matcha latte with ice', 'Matcha, Milk, Ice')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Sweet Iced Coffee', 3.7, 'Sweetened cold coffee', 'Coffee, Ice, Sugar Syrup')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Chocolate Iced Coffee', 4.2, 'Cold coffee with chocolate flavor', 'Coffee, Ice, Chocolate Syrup')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Fresh Sweet Lemonade', 2.5, 'Refreshing lemonade', 'Lemon, Sugar, Water')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Hot Tea', 1.5, 'Traditional hot tea', 'Tea Leaves, Water')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Chocolate Cake', 5.0, 'Rich chocolate cake', 'Flour, Cocoa, Sugar, Eggs')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Muffins', 3.0, 'Soft and fluffy muffins', 'Flour, Sugar, Eggs, Butter')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Strawberries Cake', 5.5, 'Delicious cake with strawberries', 'Flour, Sugar, Eggs, Strawberries')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('PanCake', 3.8, 'Fluffy pancakes', 'Flour, Milk, Eggs, Sugar, Butter')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Berry MilkShake', 4.5, 'Berry flavored milkshake', 'Milk, Berries, Sugar')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Cinnamon Rolls', 3.9, 'Sweet rolls with cinnamon', 'Flour, Sugar, Butter, Cinnamon')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Kiwi Cupcake', 2.8, 'Cupcake with kiwi flavor', 'Flour, Sugar, Eggs, Kiwi')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Vanilla Cupcake', 2.8, 'Cupcake with vanilla flavor', 'Flour, Sugar, Eggs, Vanilla')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Chocolate Macarons', 6.0, 'Delicate chocolate macarons', 'Almond Flour, Sugar, Cocoa, Eggs')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Hot Chocolate', 2.0, 'Warm and comforting hot chocolate', 'Milk, Cocoa, Sugar')",
                "INSERT INTO items (emri, cmimi, pershkrimi, perbersit) VALUES ('Black Tea', 1.8, 'Bold and rich black tea', 'Black Tea Leaves, Water')"
        };

        for (String item : items) {
            db.execSQL(item);
        }
    }
}
