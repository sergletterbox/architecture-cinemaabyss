package com.example.eventsservice.service;

import com.example.eventsservice.model.BaseEvent;
import com.example.eventsservice.model.MovieEvent;
import com.example.eventsservice.model.PaymentEvent;
import com.example.eventsservice.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.user-events}")
    private String userEventsTopic;

    @Value("${kafka.topics.movie-events}")
    private String movieEventsTopic;

    @Value("${kafka.topics.payment-events}")
    private String paymentEventsTopic;

    public EventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(UserEvent event) {
        sendEvent(userEventsTopic, event.getUserId(), event);
    }

    public void sendMovieEvent(MovieEvent event) {
        sendEvent(movieEventsTopic, event.getMovieId(), event);
    }

    public void sendPaymentEvent(PaymentEvent event) {
        sendEvent(paymentEventsTopic, event.getPaymentId(), event);
    }

    private void sendEvent(String topic, String key, BaseEvent event) {
        logger.info("Отправка события в топик {}: eventId={}, eventType={}",
                topic, event.getId(), event.getEventType());

        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(topic, key, event);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                logger.error("Ошибка отправки события в топик {}: eventId={}, error={}",
                        topic, event.getId(), exception.getMessage(), exception);
            } else {
                logger.info("Событие успешно отправлено в топик {}: eventId={}, partition={}, offset={}",
                        topic, event.getId(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}