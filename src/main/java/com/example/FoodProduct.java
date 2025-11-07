package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class FoodProduct extends Product implements Perishable, Shippable {
    private final LocalDate expirationDate;
    private final BigDecimal weight; // in kg

    public FoodProduct(UUID uuid, String name, Category category, BigDecimal price, LocalDate expirationDate, BigDecimal weight) {
        super(uuid, name, category, price);
        if (expirationDate == null) throw new IllegalArgumentException("Expiration date cannot be null.");
        if (weight == null || weight.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Weight cannot be negative.");
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public LocalDate expirationDate() { return expirationDate; }

    @Override
    public Double weight() { return weight.doubleValue(); }

    @Override
    public BigDecimal calculateShippingCost() {
        // Simplified: shipping cost = weight * 50
        return weight.multiply(BigDecimal.valueOf(50));
    }

    @Override
    public String productDetails() {
        return "Food: " + name() + ", Expires: " + expirationDate;
    }
}



