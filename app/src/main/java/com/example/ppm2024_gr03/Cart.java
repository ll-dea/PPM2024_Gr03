package com.example.ppm2024_gr03;

import java.util.ArrayList;
import java.util.List;
import com.example.ppm2024_gr03.CartItem;

public class Cart {
    private static Cart instance;
    private List<CartItem> items;

    private Cart() {
        items = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void clearCart() {
        items.clear();
    }

    // This method calculates and returns the total price of all items in the cart.
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}

