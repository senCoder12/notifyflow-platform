package com.notifyflow.delivery.producer;

import org.springframework.kafka.core.KafkaTemplate;

import com.notifyflow.delivery.model.DeliveryStatusEvent;

public class DeliveryStatusProducer {
    private final KafkaTemplate<String, DeliveryStatusEvent> kafkaTemplate;

    public DeliveryStatusProducer(KafkaTemplate<String, DeliveryStatusEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(DeliveryStatusEvent event) {
        kafkaTemplate.send("delivery-status", event.getUserId(), event);
    }
}
