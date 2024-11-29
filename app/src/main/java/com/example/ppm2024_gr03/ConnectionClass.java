package com.example.ppm2024_gr03;

import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionClass {

    private static final String DB_URL = "jdbc:mysql://<127.0.0.1>:3306/pajisjemobile";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2302";

    public Connection CONN() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}