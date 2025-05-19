package com.example.adapter;

import com.example.model.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

class OrderFileAdapterTest {

    @Test
    void testReadOrdersFromCsv() throws IOException {
        OrderFileAdapter adapter = new CsvOrderAdapter();
        List<Order> orders = adapter.readOrders("src/test/resources/orders.csv");
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    void testReadOrdersFromPipeDelimitedFile() throws IOException {
        OrderFileAdapter adapter = new PipeDelimitedOrderAdapter();
        List<Order> orders = adapter.readOrders("src/test/resources/orders_pipe.txt");
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    void testReadOrdersFromHashDelimitedFile() throws IOException {
        OrderFileAdapter adapter = new HashDelimitedOrderAdapter();
        List<Order> orders = adapter.readOrders("src/test/resources/orders_hash.txt");
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    void testReadOrdersFromUnknownFormat() {
        OrderFileAdapter adapter = new PipeDelimitedOrderAdapter();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adapter.readOrders("src/test/resources/unknown_format.txt");
        });
        assertEquals("Неизвестный формат файла", exception.getMessage());
    }

    @Test
    void testReadOrdersFromNonExistentFile() {
        OrderFileAdapter adapter = new PipeDelimitedOrderAdapter();
        Exception exception = assertThrows(IOException.class, () -> {
            adapter.readOrders("src/test/resources/non_existent_file.txt");
        });
        assertNotNull(exception);
    }
}
