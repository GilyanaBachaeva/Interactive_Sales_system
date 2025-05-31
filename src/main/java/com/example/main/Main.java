package com.example.main;

import com.example.adapter.IORuntimeException;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import com.example.adapter.OrderAdapterService;
import com.example.service.OrderManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, IORuntimeException {

        String inputFilePath = "orders.txt";
        String outputFilePath = "orders_report.txt";

        OrderAdapterService orderAdapterService = new OrderAdapterService();


        OrderService orderService = new OrderService();
        OrderRepository orderRepository = new OrderRepository();


        OrderManager orderManager = new OrderManager(orderAdapterService, orderService, orderRepository, outputFilePath);


        orderManager.processOrders(inputFilePath, "orders_report.txt",50, 5);
    }
}
