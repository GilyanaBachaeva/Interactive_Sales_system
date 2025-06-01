package com.example.service;

import com.example.adapter.OrderAdapterService;
import com.example.adapter.OrderFileAdapter;
import com.example.adapter.OrderProcessRuntimeException;
import com.example.model.Order;
import com.example.model.OrderSummary;
import com.example.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderManagerTest {
    private OrderAdapterService adapterService;
    private OrderService orderService;
    private OrderRepository orderRepository;
    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        adapterService = mock(OrderAdapterService.class);
        orderService = mock(OrderService.class);
        orderRepository = mock(OrderRepository.class);
        orderManager = new OrderManager(adapterService, orderService, orderRepository, "outputtest.txt");
    }

    @Test
    void processOrders_shouldInvokeAllMethods_withCorrectsParameters () throws IOException {
        String inputFilePath = getClass().getResource("/orders.txt").getPath();
        double initialDiscount = 50.0;
        double discountStep = 5.0;

        Path tempOutputFile = Files.createTempFile("output", ".txt");
        String outputFilePath = tempOutputFile.toString();

        OrderFileAdapter adapter = mock(OrderFileAdapter.class);
        List<Order> orders = Arrays.asList(
                new Order(LocalDateTime.parse("2021-02-09T16:00:22"), "Industrial", 8800),
                new Order(LocalDateTime.parse("2021-02-09T08:42:59"), "Power Engineer", 17480)
        );
        OrderSummary orderSummary = new OrderSummary();

        when(adapterService.getAdapter(inputFilePath)).thenReturn(adapter);
        when(adapter.readOrders(inputFilePath)).thenReturn(orders);
        when(orderService.processOrders(orders, initialDiscount, discountStep)).thenReturn(orderSummary);

        orderManager.processOrders(inputFilePath, outputFilePath, initialDiscount, discountStep);

        verify(adapterService).getAdapter(inputFilePath);
        verify(adapter).readOrders(inputFilePath);
        verify(orderService).processOrders(orders, initialDiscount, discountStep);
        verify(orderRepository).saveSummary(outputFilePath, orderSummary);

        Files.deleteIfExists(tempOutputFile);
    }

    @Test
    void processOrders_shouldThrowsException_whenAdapterFails() {
        String inputFilePath = "test.txt";
        when(adapterService.getAdapter(inputFilePath)).thenThrow(new RuntimeException("Adapter error"));

        OrderProcessRuntimeException exception = assertThrows(OrderProcessRuntimeException.class, () -> {
            orderManager.processOrders(inputFilePath, "output.txt", 0.1, 0.05);
        });

        assertEquals("Список заказов не может быть прочитан или он пуст", exception.getMessage());
    }

    @Test
    void processOrders_shouldThrowsException_whenReadingOrdersFails() throws Exception {
        String inputFilePath = "test.txt";
        OrderFileAdapter adapter = mock(OrderFileAdapter.class);
        when(adapterService.getAdapter(inputFilePath)).thenReturn(adapter);
        when(adapter.readOrders(inputFilePath)).thenThrow(new RuntimeException("Read error"));

        OrderProcessRuntimeException exception = assertThrows(OrderProcessRuntimeException.class, () -> {
            orderManager.processOrders(inputFilePath, "output.txt", 0.1, 0.05);
        });

        assertEquals("Список заказов не может быть прочитан или он пуст", exception.getMessage());
    }
}
