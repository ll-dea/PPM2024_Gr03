package com.example.ppm2024_gr03;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;
public class DB extends SQLiteOpenHelper {

    public static final String DBNAME="users.db";

    public DB(@Nullable Context context) {
        super(context,"users.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(email TEXT PRIMARY KEY, password TEXT, name TEXT, surname TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE adminUser(email TEXT PRIMARY KEY, password TEXT, name TEXT, surname TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT)");

        // Shto admin-in statik
        String adminEmail = "admin@example.com";
        String adminPassword = BCrypt.hashpw("Admin@123", BCrypt.gensalt());
        String adminName = "Admin";
        String adminSurname = "User";
        String adminPhone = "1234567890";

        db.execSQL("INSERT INTO adminUser (email, password, name, surname, phone) VALUES (?, ?, ?, ?, ?)",
                new Object[]{adminEmail, adminPassword, adminName, adminSurname, adminPhone});
    }

    private static final int DATABASE_VERSION = 1;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS adminUser");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }
    // Kontrollo nëse është admin
    public Boolean checkAdminEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM adminUser WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Merr emrin e përdoruesit ose admin-it
    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Kontrollo në tabelën 'users'
        Cursor userCursor = db.rawQuery("SELECT name FROM users WHERE email = ?", new String[]{email});
        if (userCursor.moveToFirst()) {
            String name = userCursor.getString(0);
            userCursor.close();
            return name;
        }
        userCursor.close();

        // Nëse nuk gjendet në 'users', kontrollo në tabelën 'adminUser'
        Cursor adminCursor = db.rawQuery("SELECT name FROM adminUser WHERE email = ?", new String[]{email});
        if (adminCursor.moveToFirst()) {
            String name = adminCursor.getString(0);
            adminCursor.close();
            return name;
        }
        adminCursor.close();

        // Nëse nuk gjendet as në njërën tabelë
        return null;
    }

    public Boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Check the `users` table
        Cursor userCursor = db.rawQuery("SELECT password FROM users WHERE email=?", new String[]{email});
        if (userCursor.moveToFirst()) {
            String storedHashedPassword = userCursor.getString(0);
            userCursor.close();

            // Validate hashed password
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

            // Validate hashed password
            if (BCrypt.checkpw(password, storedHashedPassword)) {
                return true;
            }
        }
        adminCursor.close();

        // If neither table has the email/password combination
        return false;
    }


    // NE TABELEN USERS RUHEN TE DHENAT QE SHKRUN NE SIGNUP , ADMIN CAKTOJM VETEM ME KOD
    public Boolean insertAdminUser(String email, String password, String name, String surname, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Kontrollo që email dhe password të mos jenë bosh
        if (email == null || password == null || name == null || surname == null || phone == null) {
            return false;
        }

        // Gjenero hashed password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Vendos vlerat
        contentValues.put("email", email);
        contentValues.put("password", hashedPassword);
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("phone", phone);

        // Fillo futjen
        long result = db.insert("users", null, contentValues);

        // Kontrollo nëse futja dështoi
        return result != -1;
    }


}