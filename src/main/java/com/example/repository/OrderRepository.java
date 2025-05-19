package com.example.repository;

import com.example.model.Order;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.example.report.OrderReport.decimalFormat;

public class OrderRepository {
    public void saveOrdersToFile(List<Order> orders, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Order order : orders) {
                String discountPercentage;
                if (order.getDiscount() == 0) {
                    discountPercentage = "0%";
                } else {
                    discountPercentage = decimalFormat.format(order.getDiscount() * 100) + "%";
                }
                writer.write("Дата заказа: " + order.getTimestamp() +
                        " |Название фирмы заказчика: " + order.getCompanyName() +
                        " |Кол-во цемента (кг): " + order.getQuantity() +
                        " |Размер скидки: " + discountPercentage);
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Недопустимый путь для сохранения файла: " + filePath, e);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
