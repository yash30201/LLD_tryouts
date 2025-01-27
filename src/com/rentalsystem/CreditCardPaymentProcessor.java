package com.rentalsystem;

import com.rentalsystem.Model.Customer;
import com.rentalsystem.Model.PaymentProcessor;

public class CreditCardPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean transact(Customer customer, long amount) {
        return true;
    }
}
