package com.example.tacocloud.repository.impl;

import com.example.tacocloud.model.Order;
import com.example.tacocloud.model.Taco;
import com.example.tacocloud.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    private SimpleJdbcInsert orderJdbc;

    private SimpleJdbcInsert orderTacosJdbc;

    private ObjectMapper objectMapper;

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbc) {
        this.orderJdbc = new SimpleJdbcInsert(jdbc)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");

        this.orderTacosJdbc = new SimpleJdbcInsert(jdbc)
                .withTableName("order_tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setCreateDt(new Date());
        long orderId = saveOrder(order);
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        for(Taco taco: tacos) {
            saveTacosToOrder(orderId, taco);
        }
        return order;
    }

    private long saveOrder(Order order) {
        try{

            Map<String, Object> values = objectMapper.convertValue(order, Map.class);
            values.put("createDt", order.getCreateDt());

            long orderId = orderJdbc
                    .executeAndReturnKey(values)
                    .longValue();
            logger.info("Saved order id:{}, name:{}", orderId, order.getOrderName());
            return orderId;
        } catch (Exception e) {
            logger.error("Error in save order name:{}", order.getOrderName(), e);
            return Long.valueOf(-1);
        }

    }

    private void saveTacosToOrder(long orderId, Taco taco) {
        Map<String, Object> values = new HashMap<>();
        values.put("orderId", orderId);
        values.put("tacoId", taco.getId());

        orderTacosJdbc.execute(values);
    }
}
