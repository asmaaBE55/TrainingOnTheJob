package com.tacos.tacocloud_nosql.service;

import com.tacos.tacocloud_nosql.model.TacoOrder;
import com.tacos.tacocloud_nosql.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<TacoOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public TacoOrder getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public TacoOrder createOrder(TacoOrder tacoOrder) {
        return orderRepository.save(tacoOrder);
    }

}
