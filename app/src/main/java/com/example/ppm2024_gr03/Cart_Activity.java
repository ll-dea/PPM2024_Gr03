package com.example.ppm2024_gr03;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Cart_Activity extends AppCompatActivity {
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        int tableId = getIntent().getIntExtra("TABLE_ID", -1);
        CartManager.getInstance().setCurrentTableId(String.valueOf(tableId));
        cartItems = CartManager.getInstance().getCartItemsForTable();

        cartAdapter = new CartAdapter(this, cartItems);

        Button goToHomeButton = findViewById(R.id.GoToHomeButton);
        goToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Cart_Activity.this, AdminPage.class);
            intent.putExtra("TABLE_ID", tableId);
            startActivity(intent);
        });

        ListView listView = findViewById(R.id.cartListView);
        TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);
        Button checkoutButton = findViewById(R.id.checkoutButton);
        Button clearCartButton = findViewById(R.id.clearCartButton);

        listView.setAdapter(cartAdapter);
        updateTotalPrice(totalPriceTextView);

        clearCartButton.setOnClickListener(v -> {
            CartManager.getInstance().clearCurrentTableCart();
            cartAdapter.notifyDataSetChanged();
            updateTotalPrice(totalPriceTextView);
        });

        checkoutButton.setOnClickListener(v -> {
            // Finalize order logic
        });
    }

    private void updateTotalPrice(TextView totalPriceTextView) {
        double total = CartManager.getInstance().getTotalPriceForCurrentTable();
        totalPriceTextView.setText("Total: $" + total);
    }
}
