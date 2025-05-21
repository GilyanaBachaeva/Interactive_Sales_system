package com.example.main;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import com.example.adapter.OrderAdapterService;
import com.example.service.OrderManager;
import com.example.adapter.OrderFileAdapter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Шаг 1: Определяем путь к файлу с заказами
        String inputFilePath = "orders.txt";

        // Создание экземпляра OrderAdapterService
        OrderAdapterService orderAdapterService = new OrderAdapterService();

        // Получение адаптера в зависимости от формата файла
        OrderFileAdapter adapter = orderAdapterService.getAdapter(inputFilePath);

        // Создание экземпляров бизнес-логики
        OrderService orderService = new OrderService();
        OrderRepository orderRepository = new OrderRepository();

        // Создание экземпляра OrderManager с инъекцией зависимостей
        OrderManager orderManager = new OrderManager(adapter, orderService, orderRepository);

        // Вызов метода для обработки заказов
        orderManager.processOrders(inputFilePath);
    }
}
