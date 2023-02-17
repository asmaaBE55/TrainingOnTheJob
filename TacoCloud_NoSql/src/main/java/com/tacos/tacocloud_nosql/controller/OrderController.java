package com.tacos.tacocloud_nosql.controller;


import com.tacos.tacocloud_nosql.model.TacoOrder;
import com.tacos.tacocloud_nosql.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = "application/json")
    public List<TacoOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(path = "/{orderId}", produces = "application/json")
    public TacoOrder getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder createOrder(@RequestBody TacoOrder tacoOrder) {
        return orderService.createOrder(tacoOrder);
    }

}
