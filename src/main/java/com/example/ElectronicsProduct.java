package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable {
    private final int warrantyMonths;
    private final BigDecimal weight; // in kg

    public ElectronicsProduct(UUID uuid, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        super(uuid, name, category, price);
        if (warrantyMonths < 0) throw new IllegalArgumentException("Warranty months cannot be negative.");
        if (weight == null || weight.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Weight cannot be negative.");
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
    }

    public int warrantyMonths() { return warrantyMonths; }

    @Override
    public Double weight() { return weight.doubleValue(); }

    @Override
    public BigDecimal calculateShippingCost() {
        // Simplified: shipping cost = 79 + 49 * (weight - 5) if weight > 5kg, else 79
        if (weight.doubleValue() > 5) {
            return BigDecimal.valueOf(79 + 49 * (weight.doubleValue() - 5));
        } else {
            return BigDecimal.valueOf(79);
        }
    }

    @Override
    public String productDetails() {
        return "Electronics: " + name() + ", Warranty: " + warrantyMonths + " months";
    }
}


