package com.example.ecommerce;

import com.example.ecommerce.category.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(

       Integer id,
     @NotNull(message = "Product name is required")
     String name,
    @NotNull(message = "Product description is required")
    String description,
      @Positive(message = "Product quantity should be positive")
      double availableQuantity,
        @Positive(message = "Price should be positive")
        BigDecimal price,
       @NotNull(message = "Category of Product should not be Null")
       Integer categoryId

) {
}