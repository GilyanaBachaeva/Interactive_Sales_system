package com.example.service;

import com.example.model.Order;
import com.example.model.OrderSummary;
import com.example.repository.OrderRepository;
import com.example.adapter.OrderFileAdapter;

import java.io.IOException;
import java.util.List;

public class OrderManager {
    private final OrderFileAdapter adapter;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderManager(OrderFileAdapter adapter, OrderService orderService, OrderRepository orderRepository) {
        this.adapter = adapter;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    public void processOrders(String inputFilePath) throws IOException {
        // Чтение заказов из файла
        List<Order> orders = adapter.readOrders(inputFilePath);

        // Создаем объект OrderSummary для хранения итогов
        OrderSummary orderSummary = new OrderSummary();


        for (Order order : orders) {
            orderService.addOrder(order);
        }

        // Обработка заказов
        orderService.processOrders(orders, orderSummary);

        // Сохранение всех заказов в файл
        orderRepository.saveOrdersToFile(orders, orderSummary);
    }
}
