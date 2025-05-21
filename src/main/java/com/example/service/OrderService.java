package com.example.service;

import com.example.model.Order;
import com.example.model.OrderSummary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderService {
    private static final double INITIAL_DISCOUNT = 0.50; // 50%
    private static final double DISCOUNT_STEP = 0.05; // 5%
    private static final double PRICE_PER_50KG = 500.0; // цена за 50 кг

    private final List<Order> orders = new ArrayList<>(); // Список заказов

    public void addOrder(Order order) {
        orders.add(order); // Добавляем заказ в список
    }

    public List<Order> getOrders() {
        return orders; // Возвращаем список заказов
    }

    public List<Double> processOrders(List<Order> orders, OrderSummary orderSummary) {
        // Сортируем заказы по времени
        Collections.sort(orders, Comparator.comparing(Order::getTimestamp));

        List<Double> totalPrices = new ArrayList<>();
        double currentDiscount = INITIAL_DISCOUNT;

        for (Order order : orders) {
            // Проверка на отрицательное количество
            if (order.getQuantity() < 0) {
                throw new IllegalArgumentException("Количество не может быть отрицательным");
            }

            double totalPrice;

            if (totalPrices.size() < 10) { // Применяем скидку только к первым 10 заказам
                totalPrice = calculatePrice(order.getQuantity(), currentDiscount);
                orderSummary.addEntry(order.getCompanyName(), totalPrice, currentDiscount);
                currentDiscount = Math.max(0, currentDiscount - DISCOUNT_STEP); // уменьшаем скидку
            } else {
                totalPrice = calculatePrice(order.getQuantity(), 0); // Полная стоимость
                orderSummary.addEntry(order.getCompanyName(), totalPrice, 0); // Устанавливаем скидку 0
            }

            totalPrices.add(totalPrice);
        }

        return totalPrices;
    }

    public static double calculatePrice(int quantity, double discount) {
        double price = (quantity / 50) * PRICE_PER_50KG; // количество мешков
        return price * (1 - discount); // применяем скидку
    }
}
