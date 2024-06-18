package com.example.ecommmerce.kafka.order;

public record Customer(


        String id,
        String firstname,
        String lastname,
        String email
) {
}
