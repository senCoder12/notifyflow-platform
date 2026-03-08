package com.notifyflow.websocket.service;

import com.notifyflow.websocket.model.DeliveryStatusEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketPushService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketPushService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void push(DeliveryStatusEvent event) {

        String destination = "/topic/notifications/" + event.getUserId();

        messagingTemplate.convertAndSend(destination, event);
    }
}