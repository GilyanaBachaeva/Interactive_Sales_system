package com.example.repository;

import com.example.model.OrderSummary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

public class OrderRepository {
    public static final DecimalFormat TOTAL_QUANTITY_FORMAT = new DecimalFormat("#");

    public void saveSummary(String filePath, OrderSummary orderSummary) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, OrderSummary.OrderDetails> entry:

                    orderSummary.getSummary().entrySet()) {
                String companyName = entry.getKey();
                OrderSummary.OrderDetails details = entry.getValue();
                double totalPrice = details.getTotalPrice();
                double discount = details.getDiscount();
                String discountPercentage = TOTAL_QUANTITY_FORMAT.format(details.getDiscount()) + "%";

                String summaryLine = "Название фирмы заказчика: " + companyName + " | " +
                        "Итоговая цена: " + TOTAL_QUANTITY_FORMAT.format(totalPrice) + " руб. | " +
                        "Размер скидки: " + discountPercentage;
                writer.write(summaryLine);
                writer.newLine();
            }
        }
    }
}
