package com.example.ppm2024_gr03;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class AdminPage extends AppCompatActivity {
    private DB database;
    private Map<Integer, String> buttonToProductMap;
    private CartManager cartManager;
    private int currentTableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);

        currentTableId = getIntent().getIntExtra("TABLE_ID", -1);
        if (currentTableId == -1) {
            Log.e("AdminPage", "No table ID received. Exiting AdminPage.");
            finish();
            return;
        }

        cartManager = CartManager.getInstance();
        cartManager.setCurrentTableId(String.valueOf(currentTableId));

        database = new DB(this);
        buttonToProductMap = new HashMap<>();

        int[] buttonIds = {R.id.IcedCoffeeButton, R.id.IcedMatchaButton, R.id.SweetIcedButton, R.id.ChocolateIcedCoffeeButton,
                R.id.FreshSweetLemonadeButton, R.id.HotTeaButton, R.id.ChocolateCakeButton, R.id.MuffinsButton, R.id.BiscuitsButton,
                R.id.StrawberriesCakeButton, R.id.PanCakeButton, R.id.BerryMilkShakeButton, R.id.CinnamonRollsButton, R.id.KiwiCupcakeButton,
                R.id.VanillaCupcakeButton, R.id.ChocolateMacaronsButton, R.id.HotChocolateButton, R.id.BlackTeaButton};

        String[] productNames = {
                "Iced Coffee", "Iced Matcha", "Sweet Iced Coffee", "Chocolate Iced Coffee",
                "Fresh Sweet Lemonade", "Hot Tea", "Chocolate Cake", "Muffins", "Biscuits",
                "Strawberries Cake", "PanCake", "Berry MilkShake", "Cinnamon Rolls",
                "Kiwi Cupcake", "Vanilla Cupcake", "Chocolate Macarons", "Hot Chocolate", "Black Tea"
        };

        for (int i = 0; i < buttonIds.length; i++) {
            buttonToProductMap.put(buttonIds[i], productNames[i]);
            Button button = findViewById(buttonIds[i]);
            if (button != null) {
                button.setOnClickListener(new ButtonClickListener());
            } else {
                Log.e("Buttons", "Button with ID " + buttonIds[i] + " not found in layout.");
            }
        }

        Button viewCartButton = findViewById(R.id.ViewCartButton);
        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminPage.this, Cart_Activity.class);
            intent.putExtra("TABLE_ID", currentTableId);
            startActivity(intent);
        });
        Button backToTableSelectionButton = findViewById(R.id.BackToTableSelectionButton);
        backToTableSelectionButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminPage.this, TableSelectionActivity.class);
            startActivity(intent);
        });
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int buttonId = view.getId();
            String productName = buttonToProductMap.get(buttonId);

            if (productName != null) {
                Cursor cursor = database.getReadableDatabase().rawQuery(
                        "SELECT cmimi FROM items WHERE emri = ?",
                        new String[]{productName}
                );

                if (cursor.moveToFirst()) {
                    double price = cursor.getDouble(0);
                    cartManager.addItem(productName, 1, price);
                    Log.d("Cart", "Added to cart: " + productName + " - $" + price);
                } else {
                    Log.e("Cart", "Product not found in database: " + productName);
                }
                cursor.close();
            }
        }
    }
}
