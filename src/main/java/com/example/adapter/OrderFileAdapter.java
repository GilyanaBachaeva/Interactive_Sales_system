package com.example.adapter;

import com.example.model.Order;

import java.io.IOException;
import java.util.List;

public interface OrderFileAdapter {
    List<Order> readOrders(String filePath) throws IOException;
}
