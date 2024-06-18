package com.example.ecommerce.orderline;


import com.example.ecommerce.order.OrderLineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

private final OrderLineRepository orderLineRepository;
private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var orderLine=orderLineRepository.save(orderLineMapper.createOrderLine(orderLineRequest));
        return orderLineRepository.save(orderLine).getId();




    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .collect(Collectors.toList());


    }
}
