package com.example.ppm2024_gr03;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {

    private ConnectionClass connectionClass;

    public DB() {
        connectionClass = new ConnectionClass();
    }

    // Insert user data into MySQL
    public boolean insertDataMySQL(String name, String surname, String email, String phone, String password) {
        Connection connection = connectionClass.CONN();
        if (connection == null) {
            return false; // Connection failed
        }

        try {
            // Check if user already exists
            String checkQuery = "SELECT * FROM Users WHERE email = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // User exists
                return false; // Return false if user already exists
            }

            // Hash password before storing
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Insert new user
            String query = "INSERT INTO Users (username, email, phone, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name + " " + surname); // Combine name and surname
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, hashedPassword); // Store hashed password

            int rowsInserted = stmt.executeUpdate();
            stmt.close();
            connection.close();
            return rowsInserted > 0; // Return true if insertion successful
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
