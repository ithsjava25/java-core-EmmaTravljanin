package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class Warehouse {
    private static Warehouse instance;

    private final String name;
    private final Map<UUID, Product> products = new ConcurrentHashMap<>();

    private Warehouse(String name) {
        this.name = name;
    }


    public static Warehouse getInstance(String name) {
        if (instance == null) {
            instance = new Warehouse(name);
        }
        return instance;
    }

    public String getName() {
        return name;
    }


    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null.");
        products.put(product.uuid(), product);
    }

    public void remove(UUID uuid) {
        products.remove(uuid);
    }

    public Optional<Product> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void clearProducts() {
        products.clear();
    }

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }


    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        if (!products.containsKey(uuid)) {
            throw new NoSuchElementException("Product not found with id: " + uuid);
        }
        Product old = products.get(uuid);
        Product updated;

        if (old instanceof FoodProduct food) {
            updated = new FoodProduct(
                    food.uuid(),
                    food.name(),
                    food.category(),
                    newPrice,
                    food.expirationDate(),
                    BigDecimal.valueOf(food.weight())
            );
        } else if (old instanceof ElectronicsProduct elec) {
            updated = new ElectronicsProduct(
                    elec.uuid(),
                    elec.name(),
                    elec.category(),
                    newPrice,
                    elec.warrantyMonths(),
                    BigDecimal.valueOf(elec.weight())
            );
        } else {
            throw new IllegalStateException("Unknown product type.");
        }

        products.put(uuid, updated);
    }


    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(Product::category));
    }


    public List<Shippable> shippableProducts() {
        return products.values().stream()
                .filter(p -> p instanceof Shippable)
                .map(p -> (Shippable) p)
                .toList();
    }


    public List<Perishable> expiredProducts() {
        return products.values().stream()
                .filter(p -> p instanceof Perishable)
                .map(p -> (Perishable) p)
                .filter(per -> per.expirationDate().isBefore(java.time.LocalDate.now()))
                .toList();
    }
}

