package com.example.ecommerce.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer firstName is mandatory")
        String firstname,
        @NotNull(message = "Customer firstName is mandatory")
        String lastname,
        @NotNull(message = "Customer firstName is not valid ")
        String email,
        Address address

) {
}
