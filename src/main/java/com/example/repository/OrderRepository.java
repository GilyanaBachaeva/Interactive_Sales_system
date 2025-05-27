package com.example.repository;

import com.example.model.OrderSummary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class OrderRepository {
    public static final DecimalFormat TOTAL_QUANTITY_FORMAT = new DecimalFormat("#");

    public void saveSummary(String filePath, OrderSummary orderSummary) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(orderSummary.toString());
        }
    }
}
