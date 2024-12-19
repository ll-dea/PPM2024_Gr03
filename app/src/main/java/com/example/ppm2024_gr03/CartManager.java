package com.example.ppm2024_gr03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
    private final Map<String, List<CartItem>> tableCartMap;
    private String currentTableId;

    private CartManager() {
        tableCartMap = new HashMap<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public synchronized void setCurrentTableId(String tableId) {
        currentTableId = tableId;
        tableCartMap.putIfAbsent(tableId, new ArrayList<>());
    }

    public synchronized List<CartItem> getCartItemsForTable() {
        return tableCartMap.getOrDefault(currentTableId, new ArrayList<>());
    }

    public synchronized void addItem(String productName, int quantity, double price) {
        if (currentTableId == null) {
            throw new IllegalStateException("No table ID is set. Please set the current table ID first.");
        }

        List<CartItem> cart = tableCartMap.get(currentTableId);
        for (CartItem item : cart) {
            if (item.getProductName().equals(productName)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cart.add(new CartItem(productName, quantity, price));
    }

    public synchronized void clearCurrentTableCart() {
        if (currentTableId != null) {
            tableCartMap.put(currentTableId, new ArrayList<>());
        }
    }

    public synchronized double getTotalPriceForCurrentTable() {
        double total = 0;
        for (CartItem item : getCartItemsForTable()) {
            total += item.getTotalPrice();
        }
        return total;
    }
}
