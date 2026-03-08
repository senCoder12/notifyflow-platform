package com.notifyflow.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedNotificationEvent {
    private String userId;
    private String channel;
    private String formattedMessage;
    private long processedAt;
    private int retryCount;
}
