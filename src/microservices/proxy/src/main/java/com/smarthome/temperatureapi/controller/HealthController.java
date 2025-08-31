package com.smarthome.temperatureapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    private static final Logger log = LoggerFactory.getLogger(HealthController.class);

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Proxy",
                "version", "1.0.0"
        ));
    }

    @GetMapping("/ready")
    public ResponseEntity<Map<String, Object>> readinessCheck() {
        Map<String, Object> status = new HashMap<>();
        boolean isReady = true;


        status.put("status", isReady ? "READY" : "NOT_READY");
        status.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(status);
    }
}
