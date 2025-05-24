package com.example.adapter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OrderAdapterService {
    public OrderFileAdapter getAdapter(String inputFilePath) throws IORuntimeException {
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

    private String readFirstLine(String filePath) throws IORuntimeException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine();
        } catch (IOException e){
            throw new IORuntimeException("Ошибка при чтении файла: " + filePath, e);
        }
    }
}
