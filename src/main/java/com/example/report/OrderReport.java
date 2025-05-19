package com.example.report;

import com.example.model.Order;
import com.example.model.OrderSummary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderReport {
    public static final DecimalFormat decimalFormat = new DecimalFormat("#.00"); // Формат для округления до двух знаков после запятой

    public Map<String, OrderSummary> generateReport(List<Order> orders, List<Double> totalPrices) {
        Map<String, OrderSummary> report = new HashMap<>();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            double totalPrice = totalPrices.get(i);
            OrderSummary summary = report.getOrDefault(order.getCompanyName(), new OrderSummary());
            summary.addOrder(totalPrice);
            report.put(order.getCompanyName(), summary);
        }

        return report;
    }

    public void saveReportToFile(Map<String, OrderSummary> report, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, OrderSummary> entry : report.entrySet()) {
                // Округляем значение до двух знаков после запятой
                String formattedValue = decimalFormat.format(entry.getValue().getTotalQuantity());
                int orderCount = entry.getValue().getOrderCount();
                writer.write("Заказчик: " + entry.getKey() + " - " + formattedValue + " кг, Количество заказов: " + orderCount);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
