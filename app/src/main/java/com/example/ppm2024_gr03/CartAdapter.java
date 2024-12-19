package com.example.ppm2024_gr03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends ArrayAdapter<CartItem> {
    private List<CartItem> cartItems; // List to store the cart items

    public CartAdapter(Context context, List<CartItem> cartItems) {
        super(context, 0, cartItems);
        this.cartItems = new ArrayList<>(cartItems); // Initialize the list
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }

        CartItem cartItem = getItem(position);

        if (cartItem != null) {
            TextView nameTextView = convertView.findViewById(R.id.itemNameTextView);
            TextView quantityTextView = convertView.findViewById(R.id.itemQuantityTextView);
            TextView priceTextView = convertView.findViewById(R.id.itemPriceTextView);

            nameTextView.setText(cartItem.getProductName());
            quantityTextView.setText("Quantity: " + cartItem.getQuantity());
            priceTextView.setText(String.format("Price: $%.2f", cartItem.getTotalPrice()));
        }

        return convertView;
    }
}
