package com.notifyflow.delivery.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.notifyflow.delivery.model.ProcessedNotificationEvent;
import com.notifyflow.delivery.service.NotificationDeliveryService;

@Component
public class DeliveryConsumer {

    private final NotificationDeliveryService deliveryService;

    public DeliveryConsumer(NotificationDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @KafkaListener(topics = "processed-notifications", groupId = "delivery-group")
    public void consume(ProcessedNotificationEvent notification) {
        deliveryService.deliver(notification);
    }
}
