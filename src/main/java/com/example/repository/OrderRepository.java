package com.example.repository;

import com.example.model.Order;
import com.example.model.OrderSummary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class OrderRepository {
    private static final String REPORT_FILE_NAME = "orders_report.txt";
    private static final DecimalFormat TOTAL_QUANTITY_FORMAT = new DecimalFormat("#.00");

    public void saveOrdersToFile(List<Order> orders, OrderSummary orderSummary) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE_NAME))) {
            // Сначала записываем информацию о каждом заказе
            for (Order order : orders) {
                // Получаем итоговую цену для компании из OrderSummary
                OrderSummary.OrderDetails details = orderSummary.getSummary().get(order.getCompanyName());
                double totalPrice = (details != null) ? details.getTotalPrice() : 0.0; // Если нет данных, устанавливаем 0

                String line = "Дата заказа: " + order.getTimestamp() + " | " +
                        "Название фирмы заказчика: " + order.getCompanyName() + " | " +
                        "Кол-во цемента (кг): " + order.getQuantity() + " | " +
                        "Итоговая цена: " + TOTAL_QUANTITY_FORMAT.format(totalPrice)+ " руб.";
                writer.write(line);
                writer.newLine();
            }

            // Затем записываем информацию из OrderSummary
            for (Map.Entry<String, OrderSummary.OrderDetails> entry : orderSummary.getSummary().entrySet()) {
                String companyName = entry.getKey();
                OrderSummary.OrderDetails details = entry.getValue();
                double totalPrice = details.getTotalPrice();
                double discount = details.getDiscount();

                // Форматируем строку с необходимой информацией
                String summaryLine = "Название фирмы заказчика: " + companyName + " | " +
                        "Итоговая цена: " + TOTAL_QUANTITY_FORMAT.format(totalPrice) + " руб. | " +
                        "Размер скидки: " + TOTAL_QUANTITY_FORMAT.format(discount * 100) + "%";
                writer.write(summaryLine);
                writer.newLine();
            }
        }
    }
}
