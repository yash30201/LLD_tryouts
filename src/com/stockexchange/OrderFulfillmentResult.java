package com.stockexchange;

public class OrderFulfillmentResult {
    private final Order order;
    private Long qtyFulfilled;
    private Double avgPrice;

    public OrderFulfillmentResult(Order order, Long qtyFulfilled, Double avgPrice) {
        this.order = order;
        this.qtyFulfilled = qtyFulfilled;
        this.avgPrice = avgPrice;
    }

    public Order getOrder() {
        return order;
    }

    public Long getQtyFulfilled() {
        return qtyFulfilled;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setQtyFulfilled(Long qtyFulfilled) {
        this.qtyFulfilled = qtyFulfilled;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }
}
