package com.example.ppm2024_gr03;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class AdminPage extends AppCompatActivity {
    private DB database; // Database instance
    private Map<Integer, String> buttonToProductMap; // Map buttons to product names
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);

        String userName = getIntent().getStringExtra("adminName");

        Log.d("Admin-UserHomePage", "User name: " + userName);

//        TextView welcomeText = findViewById(R.id.welcomeTextView);
//        welcomeText.setText("Hello, " + userName + "!");
        database = new DB(this);
        buttonToProductMap = new HashMap<>();


        int[] buttonIds ={R.id.IcedCoffeeButton,R.id.IcedMatchaButton,R.id.SweetIcedButton,R.id.ChocolateIcedCoffeeButton,
                R.id.FreshSweetLemonadeButton,R.id.HotTeaButton,R.id.ChocolateCakeButton,R.id.MuffinsButton,R.id.BiscuitsButton,
                R.id.StrawberriesCakeButton,R.id.PanCakeButton,R.id.BerryMilkShakeButton,R.id.CinnamonRollsButton,R.id.KiwiCupcakeButton,
                R.id.VanillaCupcakeButton,R.id.ChocolateMacaronsButton,R.id.HotChocolateButton,R.id.BlackTeaButton};

        for (int id : buttonIds) {
            Button button = findViewById(id);

            if (button != null) {
                Log.d("Butonat", "Button found: " + button.getText());
                button.setOnClickListener(new ButtonClickListener());
            } else {
                // Log the missing button
                Log.e("Butonat"," Button with ID " + id + " not found in layout.");

            }
        }
        String[] productNames = {
                "Iced Coffee", "Iced Matcha", "Sweet Iced Coffee", "Chocolate Iced Coffee",
                "Fresh Sweet Lemonade", "Hot Tea", "Chocolate Cake", "Muffins","Biscuits",
                "Strawberries Cake", "PanCake", "Berry MilkShake", "Cinnamon Rolls",
                "Kiwi Cupcake", "Vanilla Cupcake", "Chocolate Macarons", "Hot Chocolate", "Black Tea"
        };
        for (int i = 0; i < buttonIds.length; i++) {
            buttonToProductMap.put(buttonIds[i], productNames[i]);
        }



        // Set up the buttons and their listeners
        Button menuButton = findViewById(R.id.MenuButton);
        Button invoicesButton = findViewById(R.id.InvoicesButton);
        Button messagesButton = findViewById(R.id.MessagesButton);

        // Set onClick listeners for each button
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPage.this, AdminPage.class); // Or the appropriate activity
                startActivity(intent);
            }
        });

        invoicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this, NewItem.class);
                startActivity(intent);
            }
        });

        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For example, navigate to the Contact activity
                Intent intent = new Intent(AdminPage.this, AdminMessages.class);
                startActivity(intent);
            }
        });
    }
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                int buttonId = view.getId();
                String productName = buttonToProductMap.get(buttonId);

                if (productName != null) {
                    // Fetch product details from the database
                    Cursor cursor = database.getReadableDatabase().rawQuery(
                            "SELECT pershkrimi, perbersit FROM items WHERE emri = ?",
                            new String[]{productName}
                    );

                    if (cursor.moveToFirst()) {
                        String description = cursor.getString(0);
                        String ingredients = cursor.getString(1);

                        // Show a dialog with product details
                        new AlertDialog.Builder(AdminPage.this)
                                .setTitle(productName)
                                .setMessage("Description: " + description + "\n\nIngredients: " + ingredients)
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        // If no details are found, show an error
                        new AlertDialog.Builder(AdminPage.this)
                                .setTitle("Error")
                                .setMessage("Details for " + productName + " not found.")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                    cursor.close();
                }
            }
        }
    }
}

