package com.notifyflow.processor.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.notifyflow.processor.model.NotificationEvent;
import com.notifyflow.processor.service.NotificationProcessingService;

@Component
public class NotificationConsumerEvent {

    private final NotificationProcessingService processingService;

    public NotificationConsumerEvent(NotificationProcessingService processingService) {
        this.processingService = processingService;
    }

    @KafkaListener(topics = "notification-events", groupId = "notification-processor-group")
    public void consume(NotificationEvent event) {
        processingService.process(event);
    }
}
