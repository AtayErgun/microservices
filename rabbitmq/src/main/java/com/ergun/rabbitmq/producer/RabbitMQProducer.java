package com.ergun.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitTemplate rabbitTemplate;

    // Constructor injection
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Method to send a simple message
    public boolean sendMessage(String message) {
        try {
            LOGGER.info(String.format("Sent message: %s", message));
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            return true; // Mesaj gönderimi başarılı
        } catch (Exception e) {
            LOGGER.error("Failed to send message: " + e.getMessage());
            return false; // Mesaj gönderimi başarısız
 }
}
}