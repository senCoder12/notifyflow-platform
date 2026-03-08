package com.notifyflow.ingestion.mapper;

import com.notifyflow.ingestion.dto.NotificationRequest;
import com.notifyflow.ingestion.model.NotificationEvent;

public class NotificationMapper {

    public static NotificationEvent toEvent(NotificationRequest request) {

        return NotificationEvent.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .message(request.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
