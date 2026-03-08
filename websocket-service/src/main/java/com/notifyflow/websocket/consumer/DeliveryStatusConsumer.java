package com.notifyflow.websocket.consumer;

import com.notifyflow.websocket.model.DeliveryStatusEvent;
import com.notifyflow.websocket.service.WebSocketPushService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeliveryStatusConsumer {

    private final WebSocketPushService pushService;

    public DeliveryStatusConsumer(WebSocketPushService pushService) {
        this.pushService = pushService;
    }

    @KafkaListener(topics = "delivery-status-events", groupId = "websocket-group")
    public void consume(DeliveryStatusEvent event) {

        pushService.push(event);
    }
}