package com.example.model;

public class OrderSummary {
    private double totalQuantity; // Общее количество в кг
    private int orderCount; // Количество заказов

    public OrderSummary() {
        this.totalQuantity = 0;
        this.orderCount = 0;
    }

    public void addOrder(double quantity) {
        this.totalQuantity += quantity;
        this.orderCount++;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
