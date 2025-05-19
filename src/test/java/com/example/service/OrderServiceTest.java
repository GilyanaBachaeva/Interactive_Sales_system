package com.example.service;

import com.example.model.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class OrderServiceTest {

    @Test
    void testProcessOrdersWithLessThan10() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<> (List.of(
                Order.fromString("2021-02-09T16:00:22|Industrial|8800"), // Первый заказ
                Order.fromString("2021-02-09T16:05:22|Industrial|7500")  // Второй заказ с меньшей ценой
        ));
        List<Double> totalPrices = OrderService.processOrders(orders);

        assertEquals(2, totalPrices.size());
        assertTrue(totalPrices.get(0) > totalPrices.get(1)); // Проверка, что скидка применяется
    }

    @Test
    void testProcessOrdersWithMoreThan10() {
        OrderService orderService = new OrderService();
        List<Order> orders = new ArrayList<>(List.of(
                Order.fromString("2021-02-09T16:00:22|Industrial|8800"),
                Order.fromString("2021-02-09T08:42:59|Power Engineer|17480"),
                Order.fromString("2021-02-09T10:48:34|Mosque|33120"),
                Order.fromString("2021-02-09T08:19:22|Recovery|11340"),
                Order.fromString("2021-02-09T11:41:31|Atomic|12500"),
                Order.fromString("2021-02-09T08:57:51|Preparatory|21410"),
                Order.fromString("2021-02-09T20:26:03|Resident|5610"),
                Order.fromString("2021-02-09T09:50:10|Fossil|19600"),
                Order.fromString("2021-02-10T08:53:25|Power Engineer|24600"),
                Order.fromString("2021-02-09T17:39:17|Carryover|29670"),
                Order.fromString("2021-02-09T12:32:48|Electricity|3680"),
                Order.fromString("2021-02-09T21:10:34|Ancillary|30000")
        ));
        List<Double> totalPrices = orderService.processOrders(orders);
        assertEquals(12, totalPrices.size());
        assertEquals(0, orders.get(10).getDiscount()); // Проверка, что скидка 0 для 11-го заказа
    }

    @Test
    void testCalculatePrice() {
        OrderService orderService = new OrderService();
        double price = orderService.calculatePrice(100, 0.1); // 100 кг с 10% скидкой
        assertEquals(1000.0 * 0.9, price); // Проверка правильности расчета
    }
}
