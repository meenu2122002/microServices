package com.example.ecommerce.orderline;


import com.example.ecommerce.order.Order;
import com.example.ecommerce.order.OrderLineRequest;
import com.example.ecommerce.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineMapper {
    public OrderLine createOrderLine(OrderLineRequest orderLineRequest) {

        return OrderLine.builder()

                .id(orderLineRequest.id())
                .productId(orderLineRequest.productId())
                .quantity(orderLineRequest.quantity())
                .order(Order.builder()
                        .id(orderLineRequest.OrderId())
                                .build()
                    )
                .build();

    }


    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getId(), orderLine.getQuantity());
    }





}
