package com.notifyflow.processor.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.notifyflow.processor.model.ProcessedNotificationEvent;

@Service
public class ProcessedNoticationProducer {

    private final KafkaTemplate<String, ProcessedNotificationEvent> kafkaTemplate;
    private static final String TOPIC = "processed-notifications";

    public ProcessedNoticationProducer(KafkaTemplate<String, ProcessedNotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(ProcessedNotificationEvent event) {
        kafkaTemplate.send(TOPIC, event.getUserId(), event);
    }

}
