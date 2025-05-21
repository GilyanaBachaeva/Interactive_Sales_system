package com.example.adapter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OrderAdapterService {
    public OrderFileAdapter getAdapter(String inputFilePath) {
        if (inputFilePath.endsWith(".csv")) {
            return new CsvOrderAdapter();
        } else if (inputFilePath.endsWith(".txt")) {
            String firstLine = readFirstLine(inputFilePath);
            if (firstLine.contains("|")) {
                return new PipeDelimitedOrderAdapter();
            } else if (firstLine.contains("#")) {
                return new HashDelimitedOrderAdapter();
            } else {
                throw new IllegalArgumentException("Неизвестный формат файла");
            }
        } else {
            throw new IllegalArgumentException("Неизвестный формат файла");
        }
    }

    private String readFirstLine(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
