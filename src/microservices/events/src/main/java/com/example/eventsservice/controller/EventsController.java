package com.example.eventsservice.controller;

import com.example.eventsservice.model.MovieEvent;
import com.example.eventsservice.model.PaymentEvent;
import com.example.eventsservice.model.UserEvent;
import com.example.eventsservice.service.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventsController {

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    private final EventProducer eventProducer;

    public EventsController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.info("Health check запрошен");

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("service", "events-service");
        response.put("version", "1.0.0");
        response.put("timestamp", LocalDateTime.now());

        logger.info("Health check выполнен успешно");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/movie")
    public ResponseEntity<Map<String, Object>> createMovieEvent() {

        try {
            MovieEvent movieEvent = new MovieEvent();
            eventProducer.sendMovieEvent(movieEvent);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Movie event created successfully");
            response.put("eventId", movieEvent.getId());
            response.put("eventType", movieEvent.getEventType());
            response.put("timestamp", movieEvent.getTimestamp());

            logger.info("Movie event успешно создано: eventId={}", movieEvent.getId());
            // Construct the URI for the newly created resource
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(movieEvent.getId())
                    .toUri();
            return ResponseEntity.status(201).body(response);

        } catch (Exception e) {
            logger.error("Ошибка создания Movie event: {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create movie event");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> createUserEvent() {
        try {
            UserEvent userEvent = new UserEvent();
            eventProducer.sendUserEvent(userEvent);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "User event created successfully");
            response.put("eventId", userEvent.getId());
            response.put("eventType", userEvent.getEventType());
            response.put("timestamp", userEvent.getTimestamp());

            logger.info("User event успешно создано: eventId={}", userEvent.getId());
            return ResponseEntity.status(201).body(response);

        } catch (Exception e) {
            logger.error("Ошибка создания User event: {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create user event");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> createPaymentEvent() {

        try {
            PaymentEvent paymentEvent = new PaymentEvent();
            eventProducer.sendPaymentEvent(paymentEvent);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Payment event created successfully");
            response.put("eventId", paymentEvent.getId());
            response.put("eventType", paymentEvent.getEventType());
            response.put("timestamp", paymentEvent.getTimestamp());

            logger.info("Payment event успешно создано: eventId={}", paymentEvent.getId());
            return ResponseEntity.status(201).body(response);

        } catch (Exception e) {
            logger.error("Ошибка создания Payment event: {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to create payment event");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}