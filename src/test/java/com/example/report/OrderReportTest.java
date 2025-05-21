/*
package com.example.report;

import com.example.model.Order;
import com.example.model.OrderSummary;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

class OrderReportTest {

    @Test
    void testGenerateReport() {
        OrderReport orderReport = new OrderReport();
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
        List<Double> totalPrices = List.of(
                5670.0,   // Recovery
                9619.0,   // Power Engineer
                12846.0,  // Preparatory
                12740.0,  // Fossil
                23284.0,  // Mosque
                9375.0,   // Atomic
                2944.0,   // Electricity
                7480.0,   // Industrial
                26703.0,  // Carryover
                5320.5,   // Resident
                24600.0,  // Power Engineer
                30000.0   // Ancillary
        );
        Map<String, OrderSummary> report = orderReport.generateReport(orders, totalPrices);

        assertNotNull(report);
        assertFalse(report.isEmpty());
        assertTrue(report.containsKey("Power Engineer"));
        assertEquals(23284.0, report.get("Atomic").getTotalQuantity());
        assertEquals(2, report.get("Power Engineer").getOrderCount());
    }

    @Test
    void testSaveReportToFile() throws IOException {
        OrderReport orderReport = new OrderReport();
        Map<String, OrderSummary> report = Map.of("Power Engineer", new OrderSummary());
        // Сохранение отчета в файл
        String fileName = "test_report.txt";
        orderReport.saveReportToFile(report, fileName);

        // Проверка, что файл был создан
        File file = new File(fileName);
        assertTrue(file.exists());

        // Дополнительная проверка: чтение содержимого файла и проверка его корректности
        List<String> lines = Files.readAllLines(file.toPath());
        assertFalse(lines.isEmpty()); // Проверка, что файл не пустой
    }
}
*/