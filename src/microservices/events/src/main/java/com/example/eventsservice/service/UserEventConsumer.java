// UserEventConsumer.java
package com.example.eventsservice.service;

import com.example.eventsservice.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserEventConsumer.class);

    @KafkaListener(topics = "${kafka.topics.user-events}",
            containerFactory = "userEventKafkaListenerContainerFactory")
    public void handleUserEvent(@Payload UserEvent event,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                @Header(KafkaHeaders.OFFSET) long offset,
                                Acknowledgment acknowledgment) {

        logger.info("=== ПОЛУЧЕНО USER EVENT ===");
        logger.info("Topic: {}, Partition: {}, Offset: {}", topic, partition, offset);
        logger.info("Event ID: {}", event.getId());
        logger.info("Event Type: {}", event.getEventType());
        logger.info("User ID: {}", event.getUserId());
        logger.info("Username: {}", event.getUsername());
        logger.info("Email: {}", event.getEmail());
        logger.info("Action: {}", event.getAction());
        logger.info("Timestamp: {}", event.getTimestamp());

        try {
            // Здесь можно добавить бизнес-логику обработки пользовательских событий
            processUserEvent(event);

            acknowledgment.acknowledge();
            logger.info("User Event успешно обработано: eventId={}", event.getId());

        } catch (Exception e) {
            logger.error("Ошибка обработки User Event: eventId={}, error={}",
                    event.getId(), e.getMessage(), e);
            // В реальном приложении здесь может быть логика повторной обработки или DLQ
        }

        logger.info("=== КОНЕЦ ОБРАБОТКИ USER EVENT ===\n");
    }

    private void processUserEvent(UserEvent event) {
        // Симуляция бизнес-логики
        switch (event.getAction()) {
            case "create":
                logger.info("Обработка создания пользователя: {}", event.getUsername());
                break;
            case "update":
                logger.info("Обработка обновления пользователя: {}", event.getUsername());
                break;
            case "delete":
                logger.info("Обработка удаления пользователя: {}", event.getUsername());
                break;
            default:
                logger.warn("Неизвестное действие для пользователя: {}", event.getAction());
        }
    }
}