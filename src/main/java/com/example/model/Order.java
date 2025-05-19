package com.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private LocalDateTime timestamp;
    private String companyName;
    private int quantity;// в кг
    private double discount;

    public Order(LocalDateTime timestamp, String companyName, int quantity) {
        this.timestamp = timestamp;
        this.companyName = companyName;
        this.quantity = quantity;
        this.discount = 0;
    }

    public static Order fromString(String orderString) { // для тестов
        String[] parts = orderString.split("\\|");
        LocalDateTime timestamp = LocalDateTime.parse(parts[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String companyName = parts[1];
        int quantity = Integer.parseInt(parts[2]);
        return new Order(timestamp, companyName, quantity);
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDiscount() {
        return discount; // Геттер для скидки
    }

    public void setDiscount(double discount) {
        this.discount = discount; // Сеттер для скидки
    }
}
