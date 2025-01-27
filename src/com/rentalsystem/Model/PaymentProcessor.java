package com.rentalsystem.Model;

public interface PaymentProcessor {
    /**
     * @param customer The customer who is liable to pay the `amount`
     * @param amount   The amount to transact from Customer
     * @return Returns `true` on successful transaction.
     */
    boolean transact(Customer customer, long amount);
}
