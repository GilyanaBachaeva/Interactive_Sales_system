package com.example.main;

import com.example.model.Order;
import com.example.model.OrderSummary;
import com.example.report.OrderReport;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import com.example.adapter.CsvOrderAdapter;
import com.example.adapter.HashDelimitedOrderAdapter;
import com.example.adapter.OrderFileAdapter;
import com.example.adapter.PipeDelimitedOrderAdapter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        // Шаг 1: Определяем путь к файлу с заказами
        String inputFilePath = "orders.txt";

        OrderFileAdapter adapter;

        // Определение адаптера в зависимости от формата файла
        if (inputFilePath.endsWith(".csv")) {
            adapter = new CsvOrderAdapter();
        } else if (inputFilePath.endsWith(".txt")) {
            String firstLine = readFirstLine(inputFilePath);
            if (firstLine.contains("|")) {
                adapter = new PipeDelimitedOrderAdapter();
            } else if (firstLine.contains("#")) {
                adapter = new HashDelimitedOrderAdapter();
            } else {
                throw new IllegalArgumentException("Неизвестный формат файла");
            }
        } else {
            throw new IllegalArgumentException("Неизвестный формат файла");
        }

        // Шаг 3: Чтение заказов из файла
        List<Order> orders = adapter.readOrders(inputFilePath);

        // Шаг 4: Создание экземпляра OrderService
        OrderService orderService = new OrderService();

        // Шаг 5: Обработка заказов и получение итоговых цен
        List<Double> totalPrices = orderService.processOrders(orders);

        // Шаг 6: Создание экземпляра OrderReport
        OrderReport orderReport = new OrderReport();

        // Шаг 7: Генерация отчета по заказам
        Map<String, OrderSummary> report = orderReport.generateReport(orders, totalPrices);

        // Шаг 8: Сохранение отчета в файл
        orderReport.saveReportToFile(report, "order_report.txt");

        // Шаг 9: Создание экземпляра OrderRepository
        OrderRepository orderRepository = new OrderRepository();

        // Шаг 10: Сохранение заказов в файл
        orderRepository.saveOrdersToFile(orders, "saved_orders.txt");
    }
    private static String readFirstLine(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
