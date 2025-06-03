package com.example.service;

import com.example.model.Order;
import com.example.model.OrderSummary;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class OrderService {
    private final static double PRICE_PER_KG = 10;

    public OrderSummary processOrders(List<Order> orders, double initialDiscount, double discountStep) {
        int availableDiscountCount = (int) Math.ceil(initialDiscount/discountStep);
        OrderSummary orderSummary = new OrderSummary();

        if (orders.isEmpty()) {
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
        orders.sort(Comparator.comparing(Order::getDateTime));

        IntStream.range(0, orders.size()).forEach(i -> {
            Order order = orders.get(i);
            double currentDiscount = (i < availableDiscountCount) ? initialDiscount - (i * discountStep) : 0;
            double totalPrice = calculatePrice(order.getQuantity(), currentDiscount);
            orderSummary.addEntry(order.getCompanyName(), totalPrice, currentDiscount);
        });

        return orderSummary;
    }

    double calculatePrice(int quantity, double discount) {
        if (discount > 0) {
            int price = (int) (quantity * PRICE_PER_KG);
            return (price - ((price * discount) / 100));
        }
        return quantity * PRICE_PER_KG;
    }
}
