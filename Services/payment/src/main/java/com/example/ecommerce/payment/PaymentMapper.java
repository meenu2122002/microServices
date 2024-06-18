package com.example.ecommerce.payment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMapper {

    public Payment toPayment(PaymentRequest paymentRequest) {
return Payment.builder().
        id(paymentRequest.id())
        .orderId(paymentRequest.orderId())
        .paymentMethod(paymentRequest.paymentMethod())
        .amount(paymentRequest.amount())
        .build();

    }
}
