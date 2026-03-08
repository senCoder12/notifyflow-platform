package com.notifyflow.ingestion.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.notifyflow.ingestion.model.NotificationEvent;

@Service
public class NotificationProducerService {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    private static final String TOPIC = "notification-events";

    public NotificationProducerService(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(NotificationEvent event) {
        kafkaTemplate.send(TOPIC, event.getUserId(), event);
    }

}
