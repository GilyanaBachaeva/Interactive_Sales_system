package com.example.repository;

import com.example.model.Order;
import com.example.model.OrderSummary;
import com.example.report.OrderReport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

class OrderRepositoryTest {

    @Test
    void testSaveOrdersToFile() throws IOException {
        OrderRepository orderRepository = new OrderRepository();
        List<Order> orders = List.of(
                Order.fromString("2021-02-09T16:00:22|Industrial|8800"),
                Order.fromString("2021-02-09T08:42:59|Power Engineer|17480"),
                Order.fromString("2021-02-09T10:48:34|Mosque|33120"),
                Order.fromString("2021-02-09T08:19:22|Recovery|11340"),
                Order.fromString("2021-02-09T11:41:31|Atomic|12500"),
                Order.fromString("2021-02-09T08:57:51|Preparatory|21410"),
                Order.fromString("2021-02-09T20:26:03|Resident|5610"),
                Order.fromString("2021-02-09T09:50:10|Fossil|19600"),
                Order.fromString("2021-02-10T08:53:25|Power Engineer|24600"),
                Order.fromString("2021-02-09T17:39:17|Carryover|29670"),
                Order.fromString("2021-02-09T12:32:48|Electricity|3680"),
                Order.fromString("2021-02-09T21:10:34|Ancillary|30000")
        );
        orderRepository.saveOrdersToFile(orders, "saved_orders.txt");

        // Проверка, что файл создан и содержит данные
        try (BufferedReader reader = new BufferedReader(new FileReader("saved_orders.txt"))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
            }
            assertTrue(lineCount > 0); // Проверка, что файл не пустой
        } catch (IOException e) {
            fail("IOException occurred while reading the saved orders file.");
        }
    }

    @Test
    void testSaveReportToFile() {
        OrderReport orderReport = new OrderReport();
        Map<String, OrderSummary> report = Map.of("2021-02-09T21:10:34|Ancillary|30000", new OrderSummary());
        orderReport.saveReportToFile(report, "test_report.txt");

        // Проверка, что файл создан и содержит данные
        try (BufferedReader reader = new BufferedReader(new FileReader("test_report.txt"))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
            }
            assertTrue(lineCount > 0); // Проверка, что файл не пустой
        } catch (IOException e) {
            fail("IOException occurred while reading the report file.");
        }
    }
}
