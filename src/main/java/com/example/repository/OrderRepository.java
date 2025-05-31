package com.example.repository;

import com.example.model.OrderSummary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OrderRepository {

    public void saveSummary(String filePath, OrderSummary orderSummary) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(orderSummary.toString());
        }
    }
}
