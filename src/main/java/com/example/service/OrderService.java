package com.example.service;

import com.example.model.Order;
import com.example.model.OrderSummary;

import java.util.Comparator;
import java.util.List;

public class OrderService {
    final double PRICE_PER_KG = 10;

    public OrderSummary processOrders(List<Order> orders, double initialDiscount, double discountStep) {
        int availableDiscountCount = (int) Math.ceil(initialDiscount/discountStep);
        OrderSummary orderSummary = new OrderSummary();
        double currentDiscount = initialDiscount;

        orders.sort(Comparator.comparing(Order::getDateTime));

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);

            if (order.getQuantity() < 0) {
                throw new IllegalArgumentException("Количество не может быть отрицательным");
            }

            double totalPrice;

            if (i < availableDiscountCount) {
                totalPrice = calculatePrice(order.getQuantity(), currentDiscount);
                orderSummary.addEntry(order.getCompanyName(), totalPrice, currentDiscount);
                currentDiscount -= discountStep;
            } else {
                totalPrice = calculatePrice(order.getQuantity(), 0);
                orderSummary.addEntry(order.getCompanyName(), totalPrice, 0);
            }
        }
        return orderSummary;
    }

    double calculatePrice(int quantity, double discount) {
        if (discount > 0) {
            double price = quantity * PRICE_PER_KG;
            return price * (discount / 100);
        }
        return quantity * PRICE_PER_KG;
    }
}
