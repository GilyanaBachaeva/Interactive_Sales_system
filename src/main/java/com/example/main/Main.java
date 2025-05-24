package com.example.main;

import com.example.adapter.IORuntimeException;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import com.example.adapter.OrderAdapterService;
import com.example.service.OrderManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, IORuntimeException {
        // Шаг 1: Определяем путь к файлу с заказами
        String inputFilePath = "orders.txt";

        // Создание экземпляра OrderAdapterService
        OrderAdapterService orderAdapterService = new OrderAdapterService();

        // Создание экземпляров бизнес-логики
        OrderService orderService = new OrderService();
        OrderRepository orderRepository = new OrderRepository();

        // Создание экземпляра OrderManager с инъекцией зависимостей
        OrderManager orderManager = new OrderManager(orderAdapterService, orderService, orderRepository);

        // Вызов метода для обработки заказов
        orderManager.processOrders(inputFilePath, 50, 5);
    }
}
