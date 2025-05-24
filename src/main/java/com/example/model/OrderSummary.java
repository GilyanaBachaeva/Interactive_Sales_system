package com.example.model;

import java.util.HashMap;
import java.util.Map;

import static com.example.repository.OrderRepository.TOTAL_QUANTITY_FORMAT;

public class OrderSummary {
    private static Map<String, OrderDetails> summary;

    public OrderSummary() {
        this.summary = new HashMap<>();
    }

    public void addEntry(String companyName, double totalPrice, double discount) {
        if (summary.containsKey(companyName)) {
            OrderDetails existingDetails = summary.get(companyName);
            double newTotalPrice = existingDetails.getTotalPrice() + totalPrice;
            summary.put(companyName, new OrderDetails(newTotalPrice, existingDetails.getDiscount() + discount));
        } else {
            summary.put(companyName, new OrderDetails(totalPrice, discount));
        }
    }

    public Map<String, OrderDetails> getSummary() {
        return summary;
    }

    public static class OrderDetails {
        private double totalPrice;
        private double discount;

        public OrderDetails(double totalPrice, double discount) {
            this.totalPrice = totalPrice;
            this.discount = discount;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public double getDiscount() {
            return discount;
        }
        @Override
        public String toString() {
            StringBuilder summaryBuilder = new StringBuilder();
            for (Map.Entry<String, OrderDetails> entry : summary.entrySet()) {
                String companyName = entry.getKey();
                OrderDetails details = entry.getValue();
                summaryBuilder.append("Название фирмы заказчика: ").append(companyName)
                        .append(" | Итоговая цена: ").append(TOTAL_QUANTITY_FORMAT.format(details.getTotalPrice()))
                        .append(" руб. | Размер скидки: ").append(TOTAL_QUANTITY_FORMAT.format(details.getDiscount() * 100)).append("%\n");
            }
            return summaryBuilder.toString();
        }
    }
}
