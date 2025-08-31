package com.example.eventsservice.service;

import com.example.eventsservice.model.MovieEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MovieEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MovieEventConsumer.class);

    @KafkaListener(topics = "${kafka.topics.movie-events}",
            containerFactory = "movieEventKafkaListenerContainerFactory")
    public void handleMovieEvent(@Payload MovieEvent event,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                 @Header(KafkaHeaders.OFFSET) long offset,
                                 Acknowledgment acknowledgment) {

        logger.info("=== ПОЛУЧЕНО MOVIE EVENT ===");
        logger.info("Topic: {}, Partition: {}, Offset: {}", topic, partition, offset);
        logger.info("Event ID: {}", event.getId());
        logger.info("Event Type: {}", event.getEventType());
        logger.info("Movie ID: {}", event.getMovieId());
        logger.info("Title: {}", event.getTitle());
        logger.info("Genre: {}", event.getGenre());
        logger.info("Duration: {} minutes", event.getDuration());
        logger.info("Price: {} {}", event.getPrice(), "RUB");
        logger.info("Action: {}", event.getAction());
        logger.info("Timestamp: {}", event.getTimestamp());

        try {
            processMovieEvent(event);

            acknowledgment.acknowledge();
            logger.info("Movie Event успешно обработано: eventId={}", event.getId());

        } catch (Exception e) {
            logger.error("Ошибка обработки Movie Event: eventId={}, error={}",
                    event.getId(), e.getMessage(), e);
        }

        logger.info("=== КОНЕЦ ОБРАБОТКИ MOVIE EVENT ===\n");
    }

    private void processMovieEvent(MovieEvent event) {
        switch (event.getAction()) {
            case "create":
                logger.info("Обработка создания фильма: {}", event.getTitle());
                break;
            case "update":
                logger.info("Обработка обновления фильма: {}", event.getTitle());
                break;
            case "delete":
                logger.info("Обработка удаления фильма: {}", event.getTitle());
                break;
            default:
                logger.warn("Неизвестное действие для фильма: {}", event.getAction());
        }
    }
}