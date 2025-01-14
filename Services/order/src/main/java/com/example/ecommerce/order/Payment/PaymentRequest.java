package com.example.ecommerce.order.Payment;

import com.example.ecommerce.customer.CustomerResponse;
import com.example.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(



        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
