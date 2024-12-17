package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Cart_Activity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private TextView totalPriceTextView;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        // Initialize RecyclerView
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch cart items
        List<CartItem> cartItems = Cart.getInstance().getItems();

        // Set up adapter
        adapter = new CartAdapter(cartItems);
        cartRecyclerView.setAdapter(adapter);

        // Display total price
        totalPriceTextView = findViewById(R.id.totalPrice);
        updateTotalPrice();

        // Set up Back button functionality
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity (e.g., BuyPage)
                Intent intent = new Intent(Cart_Activity.this, BuyPage.class);
                startActivity(intent);
            }
        });
    }

    // Method to update the total price displayed
    private void updateTotalPrice() {
        double total = Cart.getInstance().getTotalPrice();
        totalPriceTextView.setText(String.format("$%.2f", total));
    }
}
