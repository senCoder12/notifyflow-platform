package com.notifyflow.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryStatusEvent {
    private String userId;
    private String channel;
    private DeliveryStatus status;
    private long timestamp;
}
