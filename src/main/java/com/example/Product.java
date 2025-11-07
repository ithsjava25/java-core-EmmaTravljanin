package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Product {
    private final UUID uuid;
    private final String name;
    private final Category category;
    private final BigDecimal price;

    protected Product(UUID uuid, String name, Category category, BigDecimal price) {
        if (uuid == null) throw new IllegalArgumentException("UUID cannot be null.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be blank.");
        if (category == null) throw new IllegalArgumentException("Category cannot be null.");
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        this.uuid = uuid;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public UUID uuid() { return uuid; }
    public String name() { return name; }
    public Category category() { return category; }
    public BigDecimal price() { return price; }

    public abstract String productDetails();
}
