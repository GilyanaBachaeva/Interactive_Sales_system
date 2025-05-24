package com.example.service;

import com.example.adapter.IORuntimeException;
import com.example.adapter.OrderAdapterService;
import com.example.model.Order;
import com.example.model.OrderSummary;
import com.example.repository.OrderRepository;
import com.example.adapter.OrderFileAdapter;

import java.io.IOException;
import java.util.List;

public class OrderManager {
    private final OrderAdapterService adapterService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderManager(OrderAdapterService adapterService, OrderService orderService, OrderRepository orderRepository) {
        this.adapterService = adapterService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    public void processOrders(String inputFilePath, double initialDiscount, double discountStep) throws IOException, IORuntimeException {

        OrderFileAdapter adapter = adapterService.getAdapter(inputFilePath);

        List<Order> orders = adapter.readOrders(inputFilePath);

        OrderSummary orderSummary = orderService.processOrders(orders, initialDiscount, discountStep);

        orderRepository.saveSummary("orders_report.txt", orderSummary);
    }
}
