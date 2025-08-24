package com.example.eventsservice.service;

import com.example.eventsservice.model.PaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentEventConsumer.class);

    @KafkaListener(topics = "${kafka.topics.payment-events}",
            containerFactory = "paymentEventKafkaListenerContainerFactory")
    public void handlePaymentEvent(@Payload PaymentEvent event,
                                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                   @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                   @Header(KafkaHeaders.OFFSET) long offset,
                                   Acknowledgment acknowledgment) {

        logger.info("=== ПОЛУЧЕНО PAYMENT EVENT ===");
        logger.info("Topic: {}, Partition: {}, Offset: {}", topic, partition, offset);
        logger.info("Event ID: {}", event.getId());
        logger.info("Event Type: {}", event.getEventType());
        logger.info("Payment ID: {}", event.getPaymentId());
        logger.info("User ID: {}", event.getUserId());
        logger.info("Amount: {} {}", event.getAmount(), event.getCurrency());
        logger.info("Payment Method: {}", event.getPaymentMethod());
        logger.info("Status: {}", event.getStatus());
        logger.info("Timestamp: {}", event.getTimestamp());

        try {
            processPaymentEvent(event);

            acknowledgment.acknowledge();
            logger.info("Payment Event успешно обработано: eventId={}", event.getId());

        } catch (Exception e) {
            logger.error("Ошибка обработки Payment Event: eventId={}, error={}",
                    event.getId(), e.getMessage(), e);
        }

        logger.info("=== КОНЕЦ ОБРАБОТКИ PAYMENT EVENT ===\n");
    }

    private void processPaymentEvent(PaymentEvent event) {
        switch (event.getStatus()) {
            case "pending":
                logger.info("Обработка ожидающего платежа: {}", event.getPaymentId());
                break;
            case "success":
                logger.info("Обработка успешного платежа: {} {}", event.getAmount(), event.getCurrency());
                break;
            case "failed":
                logger.info("Обработка неудачного платежа: {}", event.getPaymentId());
                break;
            default:
                logger.warn("Неизвестный статус платежа: {}", event.getStatus());
        }
    }
}