package com.stockexchange;

import com.sun.source.tree.Tree;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NearestCompleteOrderFulfiller implements Fulfiller {
    /**
     * Ticker to all it's buy orders
     */
    private final ConcurrentMap<String, TreeSet<Order>> buyOrders;

    /**
     * Ticker to all its sell orders
     */
    private final ConcurrentMap<String, TreeSet<Order>> sellOrders;

    private final Map<String, OrderFulfillmentResult> orderIdToFulfillmentResult;

    public NearestCompleteOrderFulfiller() {
        this.buyOrders = new ConcurrentHashMap<>();
        this.sellOrders = new ConcurrentHashMap<>();
        this.orderIdToFulfillmentResult = new HashMap<>();
    }

    @Override
    public void processOrder(Order order) {
        if (order.getOrderType().equals(OrderType.BUY)) {
            processBuyOrder(order);
        } else {
            processSellOrder(order);
        }
    }

    private void processBuyOrder(Order order) {
        // Try to match any sell orders if present.
        sellOrders.computeIfPresent(order.getTicker(), (ticker, set) -> {
            List<Order> removalQueue = new ArrayList<>();
            for (var sellOrder : set.descendingSet()) {
                OrderFulfillmentResult sellResult = orderIdToFulfillmentResult.computeIfAbsent(sellOrder.getOrderId(), (key) -> new OrderFulfillmentResult(sellOrder, 0L, null));
                Long transactionQty = Math.min(sellOrder.getQty(), order.getQty());
                sellResult.setQtyFulfilled(sellResult.getQtyFulfilled() + transactionQty);
                sellOrder.setQty(sellOrder.getQty() - transactionQty);
                if (sellResult.getAvgPrice() == null) {
                    sellResult.setAvgPrice(sellOrder.getPrice());
                }
                if (sellOrder.getQty().equals(0L)) {
                    removalQueue.add(sellOrder);
                    sellOrder.getUser().onFulfillment(sellResult);
                }
                OrderFulfillmentResult buyResult = orderIdToFulfillmentResult.computeIfAbsent(order.getOrderId(), (key) -> new OrderFulfillmentResult(order, 0L, 0.0));
                buyResult.setAvgPrice((buyResult.getAvgPrice() * buyResult.getQtyFulfilled() + transactionQty * sellOrder.getPrice()) / (buyResult.getQtyFulfilled() +  transactionQty));
                buyResult.setQtyFulfilled(buyResult.getQtyFulfilled() + transactionQty);
                order.setQty(order.getQty() - transactionQty);
                if (order.getQty().equals(0L)) {
                    break;
                }
            }
            while (!removalQueue.isEmpty()) {
                var item = removalQueue.removeLast();
                set.remove(item);
            }
            return set;
        });

        if (order.getQty().equals(0L)) {
            order.getUser().onFulfillment(orderIdToFulfillmentResult.get(order.getOrderId()));
        } else {
            var buyOrderSet = buyOrders.computeIfAbsent(order.getTicker(), (orderId) -> new TreeSet<>());
            buyOrderSet.add(order);
        }
    }

    private void processSellOrder(Order order) {
        // Try to match any sell orders if present.
        buyOrders.computeIfPresent(order.getTicker(), (ticker, set) -> {
            List<Order> removalQueue = new ArrayList<>();
            for (var buyOrder : set) {
                OrderFulfillmentResult buyResult = orderIdToFulfillmentResult.computeIfAbsent(buyOrder.getOrderId(), (key) -> new OrderFulfillmentResult(buyOrder, 0L, null));
                Long transactionQty = Math.min(buyOrder.getQty(), order.getQty());
                buyResult.setQtyFulfilled(buyResult.getQtyFulfilled() + transactionQty);
                if (buyResult.getAvgPrice() == null) {
                    buyResult.setAvgPrice(buyOrder.getPrice());
                }
                buyOrder.setQty(buyOrder.getQty() - transactionQty);

                if (buyOrder.getQty().equals(0L)) {
                    removalQueue.add(buyOrder);
                    buyOrder.getUser().onFulfillment(buyResult);
                }
                OrderFulfillmentResult sellResult = orderIdToFulfillmentResult.computeIfAbsent(order.getOrderId(), (key) -> new OrderFulfillmentResult(order, 0L, 0.0));
                sellResult.setAvgPrice((sellResult.getAvgPrice() * sellResult.getQtyFulfilled() + transactionQty * buyOrder.getPrice()) / (sellResult.getQtyFulfilled() +  transactionQty));
                sellResult.setQtyFulfilled(sellResult.getQtyFulfilled() + transactionQty);
                order.setQty(order.getQty() - transactionQty);
                if (order.getQty().equals(0L)) {
                    break;
                }
            }
            while (!removalQueue.isEmpty()) {
                var item = removalQueue.removeLast();
                set.remove(item);
            }
            return set;
        });

        if (order.getQty().equals(0L)) {
            order.getUser().onFulfillment(orderIdToFulfillmentResult.get(order.getOrderId()));
        } else {
            var sellOrderSet = sellOrders.computeIfAbsent(order.getTicker(), (orderId) -> new TreeSet<>());
            sellOrderSet.add(order);
        }
    }
}
