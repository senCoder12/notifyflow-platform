package com.notifyflow.delivery.service;

import com.notifyflow.delivery.model.DeliveryStatus;
import com.notifyflow.delivery.model.DeliveryStatusEvent;
import com.notifyflow.delivery.model.ProcessedNotificationEvent;
import com.notifyflow.delivery.producer.DeadLetterProducer;
import com.notifyflow.delivery.producer.DeliveryStatusProducer;
import com.notifyflow.delivery.producer.RetryProducer;

public class NotificationDeliveryService {

    private final RetryProducer retryProducer;
    private final DeadLetterProducer deadLetterProducer;
    private final DeliveryStatusProducer statusProducer;

    public NotificationDeliveryService(RetryProducer retryProducer,
            DeadLetterProducer deadLetterProducer,
            DeliveryStatusProducer statusProducer) {
        this.retryProducer = retryProducer;
        this.deadLetterProducer = deadLetterProducer;
        this.statusProducer = statusProducer;
    }

    public void deliver(ProcessedNotificationEvent event) {
        try {
            sendEmail(event);
        } catch (Exception e) {
            if (event.getRetryCount() < 3) {
                event.setRetryCount(event.getRetryCount() + 1);
                retryProducer.send(event);
            } else {
                deadLetterProducer.send(event);
            }
        }
    }

    private void sendEmail(ProcessedNotificationEvent event) {
        System.out.println("Sending email to user: " + event.getUserId());
        System.out.println("Message: " + event.getFormattedMessage());

        DeliveryStatusEvent deliveryStatusEvent = DeliveryStatusEvent.builder()
                .userId(event.getUserId())
                .channel(event.getChannel())
                .status(DeliveryStatus.DELIVERED)
                .timestamp(System.currentTimeMillis())
                .build();

        statusProducer.send(deliveryStatusEvent);
    }

}
