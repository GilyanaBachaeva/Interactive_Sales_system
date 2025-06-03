package com.example.service;

import com.example.model.Order;
import com.example.model.OrderSummary;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @Test
    void processOrders_shouldCorrectlyCalculateDiscount_withValidInput() {
        List<Order> orders = Arrays.asList(
                new Order((LocalDateTime.parse("2021-02-09T16:00:22")), "Industrial",8800),
                new Order((LocalDateTime.parse("2021-02-09T08:42:59")), "Power Engineer", 17480),
                new Order((LocalDateTime.parse("2021-02-09T11:41:31")), "Mosque",33120)
        );
        OrderSummary summary = orderService.processOrders(orders, 50, 5);

        assertEquals(3, orders.size());

        OrderSummary.OrderDetails industrialDetails = summary.getSummary().get("Industrial");
        assertEquals(40.0, industrialDetails.getDiscount());
        assertEquals(52800.0, industrialDetails.getTotalPrice());

        OrderSummary.OrderDetails powerEngineerDetails = summary.getSummary().get("Power Engineer");
        assertEquals(50.0, powerEngineerDetails.getDiscount());
        assertEquals(87400.0, powerEngineerDetails.getTotalPrice());

        OrderSummary.OrderDetails mosqueDetails = summary.getSummary().get("Mosque");
        assertEquals(45.0, mosqueDetails.getDiscount());
        assertEquals(182160.0, mosqueDetails.getTotalPrice());
    }

    @Test
    void processOrders_shouldThrowAnException_withEmptyList() {
        List<Order> orders = Collections.emptyList();

        Exception exception = assertThrows(Exception.class, () -> {
            orderService.processOrders(orders, 20, 10);
        });

        assertEquals("Количество не может быть отрицательным", exception.getMessage());
    }

    @Test
    void processOrders_shouldCorrectlyCalculateDiscount_withNegativeDiscount() {
        List<Order> orders = Arrays.asList(
                new Order((LocalDateTime.parse("2021-02-09T16:00:22")), "Industrial",8800),
                new Order((LocalDateTime.parse("2021-02-09T08:42:59")), "Power Engineer", 17480),
                new Order((LocalDateTime.parse("2021-02-09T11:41:31")), "Mosque",33120)
        );
        OrderSummary summary = orderService.processOrders(orders, -50, 0);

        assertEquals(3, orders.size());

        OrderSummary.OrderDetails industrialDetails = summary.getSummary().get("Industrial");
        assertEquals(0.0, industrialDetails.getDiscount());
        assertEquals(88000.0, industrialDetails.getTotalPrice());

        OrderSummary.OrderDetails powerEngineerDetails = summary.getSummary().get("Power Engineer");
        assertEquals(0.0, powerEngineerDetails.getDiscount());
        assertEquals(174800.0, powerEngineerDetails.getTotalPrice());

        OrderSummary.OrderDetails mosqueDetails = summary.getSummary().get("Mosque");
        assertEquals(0.0, mosqueDetails.getDiscount());
        assertEquals(331200.0, mosqueDetails.getTotalPrice());
    }

    @Test
    void processOrders_shouldCorrectlyCalculateDiscount_withBigList() {
        List<Order> orders = Arrays.asList(
                new Order((LocalDateTime.parse("2021-02-09T16:00:22")), "Industrial",8800),
                new Order((LocalDateTime.parse("2021-02-09T08:42:59")), "Power Engineer", 17480),
                new Order((LocalDateTime.parse("2021-02-09T10:48:34")), "Mosque",33120),
                new Order((LocalDateTime.parse("2021-02-09T08:19:22")), "Recovery",11340),
                new Order((LocalDateTime.parse("2021-02-09T11:41:31")), "Atomic", 12500),
                new Order((LocalDateTime.parse("2021-02-09T08:57:51")), "Preparatory",21410),
                new Order((LocalDateTime.parse("2021-02-09T20:26:03")), "Resident",5610),
                new Order((LocalDateTime.parse("2021-02-09T09:50:10")), "Fossil", 19600),
                new Order((LocalDateTime.parse("2021-02-10T08:53:25")), "Power Engineer",24600),
                new Order((LocalDateTime.parse("2021-02-09T17:39:17")), "Carryover",29670),
                new Order((LocalDateTime.parse("2021-02-09T12:32:48")), "Electricity", 3680),
                new Order((LocalDateTime.parse("2021-02-09T21:10:34")), "Ancillary",30000),
                new Order((LocalDateTime.parse("2021-02-09T09:11:43")), "Pyramid",10100)
        );
        OrderSummary summary = orderService.processOrders(orders, 50, 5);

        assertEquals(13, orders.size());

        OrderSummary.OrderDetails recoveryDetails = summary.getSummary().get("Recovery");
        assertEquals(50.0, recoveryDetails.getDiscount());
        assertEquals(56700.0, recoveryDetails.getTotalPrice());

        OrderSummary.OrderDetails ancillaryDetails = summary.getSummary().get("Ancillary");
        assertEquals(0.0, ancillaryDetails.getDiscount());
        assertEquals(300000.0, ancillaryDetails.getTotalPrice());

        OrderSummary.OrderDetails carryoverDetails = summary.getSummary().get("Carryover");
        assertEquals(5.0, carryoverDetails.getDiscount());
        assertEquals(281865.0, carryoverDetails.getTotalPrice());
    }
}
