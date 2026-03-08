package com.notifyflow.delivery.service;

import com.notifyflow.delivery.model.ProcessedNotificationEvent;
import com.notifyflow.delivery.producer.DeadLetterProducer;
import com.notifyflow.delivery.producer.RetryProducer;

public class NotificationDeliveryService {

    private final RetryProducer retryProducer;
    private final DeadLetterProducer deadLetterProducer;

    public NotificationDeliveryService(RetryProducer retryProducer, DeadLetterProducer deadLetterProducer) {
        this.retryProducer = retryProducer;
        this.deadLetterProducer = deadLetterProducer;
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
    }

}
