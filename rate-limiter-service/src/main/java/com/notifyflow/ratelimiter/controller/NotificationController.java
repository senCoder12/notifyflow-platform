package com.notifyflow.ratelimiter.controller;

import org.springframework.web.bind.annotation.*;

import com.notifyflow.ratelimiter.model.NotificationRequest;
import com.notifyflow.ratelimiter.service.RateLimiterService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private RateLimiterService rateLimiterService;

    public NotificationController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestHeader("user-id") String userId,
            @RequestBody NotificationRequest request) {

        if (!rateLimiterService.isAllowed(userId)) {
            return ResponseEntity.status(429)
                    .body("Rate limit exceeded");
        }

        return ResponseEntity.ok("Notification accepted");
    }
}