package com.example.model;

import java.time.LocalDateTime;

public class Order {
    private LocalDateTime dateTime;
    private String companyName;
    private int quantity;

    public Order(LocalDateTime timestamp, String companyName, int quantity) {
        this.dateTime = timestamp;
        this.companyName = companyName;
        this.quantity = quantity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getQuantity() {
        return quantity;
    }
}
