package com.notifyflow.processor.service;

import org.springframework.stereotype.Service;

import com.notifyflow.processor.model.NotificationEvent;
import com.notifyflow.processor.model.ProcessedNotificationEvent;
import com.notifyflow.processor.producer.ProcessedNoticationProducer;

@Service
public class NotificationProcessingService {

    private final ProcessedNoticationProducer producer;

    public NotificationProcessingService(ProcessedNoticationProducer producer) {
        this.producer = producer;
    }

    public void process(NotificationEvent event) {
        ProcessedNotificationEvent processedEvent = ProcessedNotificationEvent.builder()
                .userId(event.getUserId())
                .channel(event.getType())
                .formattedMessage(
                        "[NotifyFlow] " + event.getMessage() + " [Processed at: " + System.currentTimeMillis() + "]")
                .processedAt(System.currentTimeMillis())
                .build();
        producer.publish(processedEvent);
    }

}
