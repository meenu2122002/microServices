package com.example.ecommerce.order;


import com.example.ecommerce.customer.CustomerClient;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.kafka.OrderConfirmation;
import com.example.ecommerce.kafka.OrderProducer;
import com.example.ecommerce.order.Payment.PaymentClient;
import com.example.ecommerce.order.Payment.PaymentRequest;
import com.example.ecommerce.orderline.OrderLineService;
import com.example.ecommerce.product.ProductClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
private final OrderProducer orderProducer;

    private final OrderLineService orderLineService;
    private final PaymentClient paymentClient;
    private final OrderMapper orderMapper;
private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    public Integer createOrder(OrderRequest request) {
//        check the customer -Openfeign

var customer=this.customerClient.findCustomerById(request.customerId())
        .orElseThrow(() -> new BusinessException("No customer exists with the provided ID"));



//        check the products -product microservice
var purchasedProducts=this.productClient.purchaseProducts(request.products());




//        persists the order
        var order=orderRepository.save(orderMapper.toOrder(request));
//        start the order lines

        for(PurchaseRequest purchaseRequest:request.products()){
            orderLineService.saveOrderLine(
                   new OrderLineRequest(
                           null,
                           order.getId(),
                           purchaseRequest.productId(),
                           purchaseRequest.quantity()
                   )
            );
        }






//        start the payment

        var id=paymentClient.payment(
                new PaymentRequest(
                   request.amount(),
                   request.paymentMethod(),
                   order.getId(),
                        order.getReference(),
                        customer

                )
        );




//        send the order confirmation-kafka
orderProducer.sendOrderConfirmation(
        new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts

        )
);








return order.getId();

    }




    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());

    }

    public OrderResponse findById(Integer id) {

        return orderRepository.findById(id)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with specified Id %d ",id)));


    }






}
