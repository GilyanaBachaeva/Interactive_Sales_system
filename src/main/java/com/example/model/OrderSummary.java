package com.example.model;

import java.util.HashMap;
import java.util.Map;

public class OrderSummary {
    private Map<String, OrderDetails> summary;

    public OrderSummary() {
        this.summary = new HashMap<>();
    }

    public void addEntry(String companyName, double totalPrice, double discount) {
        summary.put(companyName, new OrderDetails(totalPrice, discount));
    }

    public Map<String, OrderDetails> getSummary() {
        return summary;
    }

    public static class OrderDetails {
        private double totalPrice;
        private double discount;

        public OrderDetails(double totalPrice, double discount) {
            this.totalPrice = totalPrice;
            this.discount = discount;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public double getDiscount() {
            return discount;
        }
    }
}
