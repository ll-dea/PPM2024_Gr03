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

    public static final String DBNAME="login.db";

    public DB(@Nullable Context context) {
        super(context,"login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table users(email TEXT PRIMARY KEY, password TEXT)");
        db.execSQL("CREATE TABLE adminUser (email TEXT PRIMARY KEY, password TEXT, name TEXT,surname TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS adminUser");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    public boolean insertTask(String task_name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("task_name",task_name);

        long result=db.insert("tasks",null,contentValues);
        return result!= -1;

    }
    public boolean deleteTaskByName(String taskName) {
        SQLiteDatabase database = this.getWritableDatabase();

        // Fetch the ID of one matching task
        Cursor cursor = database.query("tasks", new String[]{"id"}, "task_name = ?", new String[]{taskName}, null, null, null, "1");

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();

            // Delete the task using its ID
            int rowsDeleted = database.delete("tasks", "id = ?", new String[]{String.valueOf(id)});
            return rowsDeleted > 0;
        }

        if (cursor != null) {
            cursor.close();
        }

        return false; // No matching task found
    }

    public boolean deleteTask() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Find the last task's ID (or unique identifier)
            Cursor cursor = db.rawQuery("SELECT id FROM tasks ORDER BY id DESC LIMIT 1", null);
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0); // Get the ID of the last task
                // Delete the task by its ID
                db.delete("tasks", "id = ?", new String[]{String.valueOf(id)});
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false; // No task to delete
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public Cursor getAllTasks(){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("Select * from tasks",null);
    }
    public  Boolean insertData(String email, String password){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        String hashedPassword=BCrypt.hashpw(password,BCrypt.gensalt());

        contentValues.put("email", email);
        contentValues.put("password",hashedPassword);

        long result=db.insert("users",null,contentValues);
        if(result==-1) return false;
        else return true;
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where email=?",new String[]{email});
        if (cursor.getCount()>0){
            return true;
        }else return false;

    }
    public Boolean validateUser(String email,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select password from users where email=?",new String[]{email});

        if(cursor.moveToFirst()){
            String storedHashedPassword=cursor.getString(0);
            cursor.close();

            return BCrypt.checkpw(password,storedHashedPassword);
        }
        cursor.close();
        return false;
    }
    public Boolean insertAdminUser(String email, String password, String name,String surname, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        contentValues.put("email", email);
        contentValues.put("password", hashedPassword);
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("phone", phone);

        long result = db.insert("adminUser", null, contentValues);
        return result != -1;
    }

    // Check if admin user exists by email
    public Boolean checkAdminEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM adminUser WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}