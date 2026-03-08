package com.notifyflow.ingestion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notifyflow.ingestion.dto.NotificationRequest;
import com.notifyflow.ingestion.mapper.NotificationMapper;
import com.notifyflow.ingestion.model.NotificationEvent;
import com.notifyflow.ingestion.service.NotificationProducerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationProducerService producerService;

    public NotificationController(NotificationProducerService notificationProducerService) {
        this.producerService = notificationProducerService;
    }

    @PostMapping
    public ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationRequest request) {

        NotificationEvent event = NotificationMapper.toEvent(request);
        producerService.publish(event);
        return ResponseEntity.ok("Notification sent successfully");
    }

}
