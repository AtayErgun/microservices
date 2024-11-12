package com.ergun.rabbitmq.consumer;

import com.ergun.rabbitmq.User; // User'ı içe aktar
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    // JSON mesajları dinlemek için RabbitMQ'dan gelen mesajı User olarak al
    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void receiveJsonMessage(User user) {
        LOGGER.info(String.format("Received JSON message -> %s", user.toString()));
        // Mesajı işleme logiğini buraya ekleyebilirsin
}
}