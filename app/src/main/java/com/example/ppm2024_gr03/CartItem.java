package com.example.ppm2024_gr03;

public class CartItem {
    private String name;
    private String description;
    private double price;
    private int imageResId;
    private int quantity;

    public CartItem(String name, String description, double price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
    public int getQuantity() {
        return quantity;  // Returns the quantity of the item
    }

    // Setter for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;  // Set the quantity
    }
}
