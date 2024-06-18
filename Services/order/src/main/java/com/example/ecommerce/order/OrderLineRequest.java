package com.example.ecommerce.order;

public record OrderLineRequest(
        Integer id,
        Integer OrderId,
        Integer productId,
        double quantity) {
}
