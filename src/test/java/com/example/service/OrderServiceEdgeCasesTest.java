package com.example.service;

import com.example.model.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class OrderServiceEdgeCasesTest {

    @Test
    void testProcessOrdersWithZeroQuantity() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>(List.of(
                Order.fromString("2021-02-09T16:00:22|Industrial|0")));// Заказ с нулевым количеством
        List<Double> totalPrices = orderService.processOrders(orders);
        assertEquals(1, totalPrices.size());
        assertEquals(0.0, totalPrices.get(0)); // Проверка, что цена равна 0
    }

    @Test
    void testProcessOrdersWithNegativeQuantity() {
        OrderService orderService = new OrderService();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            List<Order> orders = new ArrayList<>(List.of(
                    Order.fromString("2021-02-09T16:00:22|Industrial|-10")));// Заказ с отрицательным количеством
            orderService.processOrders(orders);
        });
        assertEquals("Количество не может быть отрицательным", exception.getMessage());
    }

    @Test
    void testProcessOrdersWithLargeQuantity() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>(List.of(
                Order.fromString("2021-02-09T16:00:22|Industrial|1000000")));; // Заказ с максимальным количеством
        List<Double> totalPrices = orderService.processOrders(orders);
        assertTrue(totalPrices.get(0) > 0); // Проверка, что цена больше 0
    }
}
