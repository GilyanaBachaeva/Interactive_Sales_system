package com.example.adapter;

import com.example.model.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class PipeDelimitedOrderAdapter implements OrderFileAdapter {
    @Override
    public List<Order> readOrders(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(this::parseOrder)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + filePath, e);
        }
    }

    private Order parseOrder(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неизвестный формат файла");
        }
        try {
            LocalDateTime timestamp = LocalDateTime.parse(parts[0]);
            String companyName = parts[1];
            int quantity = Integer.parseInt(parts[2]);
            return new Order(timestamp, companyName, quantity);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неизвестный формат даты", e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Не удалось разобрать количество", e);
        }
    }
}
