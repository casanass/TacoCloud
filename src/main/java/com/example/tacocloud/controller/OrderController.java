package com.example.tacocloud.controller;

import com.example.tacocloud.model.Order;
import com.example.tacocloud.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String showOrder(Model model) {
        logger.info("GetMapping /order/current");
        return "order";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if(errors.hasErrors()) {
            return "order";
        }

        Order savedOrder = orderRepository.save(order);
        logger.info("PostMapping /order/current");
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
