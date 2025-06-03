package com.example.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderSummary {
    private Map<String, OrderDetails> summary = new HashMap<>();
    public static final DecimalFormat TOTAL_QUANTITY_FORMAT = new DecimalFormat("#");

    public void addEntry(String companyName, double totalPrice, double discount) {
        summary.merge(companyName, new OrderDetails(totalPrice, discount), (existingDetails, newDetails) -> {
            double updateTotalPrice = existingDetails.getTotalPrice() + newDetails.getTotalPrice();
            double updateDiscount = Math.max(existingDetails.getDiscount(), newDetails.getDiscount());
            return  new OrderDetails(updateTotalPrice, updateDiscount);
        });
    }

    @Override
    public String toString() {
        return summary.entrySet().stream()
                .map(entry -> String.format("Название фирмы заказчика: %s | Итоговая цена: %s руб. | Размер скидки: %s%%",
                        entry.getKey(),
                        TOTAL_QUANTITY_FORMAT.format(entry.getValue().getTotalPrice()),
                        TOTAL_QUANTITY_FORMAT.format(entry.getValue().getDiscount())))
                .collect(Collectors.joining("\n"));
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

    }
}
