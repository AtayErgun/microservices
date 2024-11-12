package com.ergun.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    // RabbitMQ'dan gelen mesajları dinle
    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void receiveMessage(String message) {
        LOGGER.info(String.format("Received message -> %s", message));
        // Mesajı işleme logiğini buraya ekleyebilirsin
}
}