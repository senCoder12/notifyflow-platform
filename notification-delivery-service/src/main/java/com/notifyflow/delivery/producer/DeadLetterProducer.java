package com.notifyflow.delivery.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.notifyflow.delivery.model.ProcessedNotificationEvent;

import org.springframework.stereotype.Component;

@Component
public class DeadLetterProducer {

    private final KafkaTemplate<String, ProcessedNotificationEvent> kafkaTemplate;

    public DeadLetterProducer(KafkaTemplate<String, ProcessedNotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ProcessedNotificationEvent event) {

        kafkaTemplate.send("processed-notifications-dlq",
                event.getUserId(),
                event);
    }
}
