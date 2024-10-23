package com.mkappworks.orderservice.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public void saveOrderLines(List<OrderLine> orderLines) {
        orderLineRepository.saveAll(orderLines);
    }

    public List<OrderLine> findAllByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId);
    }
}
