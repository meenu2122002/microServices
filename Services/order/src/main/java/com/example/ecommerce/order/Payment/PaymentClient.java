package com.example.ecommerce.order.Payment;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-client",
        url = "${application.config.payment-url}"
)
public interface PaymentClient {

    @PostMapping
   Integer payment(@RequestBody PaymentRequest paymentRequest) ;
}
