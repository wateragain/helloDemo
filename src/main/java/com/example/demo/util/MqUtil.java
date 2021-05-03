package com.example.demo.util;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqUtil {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendTopic(String exchange, String routingKey, String data){
        amqpTemplate.convertAndSend(exchange, routingKey, data);
    }
}
