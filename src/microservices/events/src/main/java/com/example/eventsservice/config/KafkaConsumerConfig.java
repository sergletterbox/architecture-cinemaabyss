package com.example.eventsservice.config;

import com.example.eventsservice.model.MovieEvent;
import com.example.eventsservice.model.PaymentEvent;
import com.example.eventsservice.model.UserEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    // Consumer для UserEvent
    @Bean
    public ConsumerFactory<String, UserEvent> userEventConsumerFactory() {
        Map<String, Object> props = getProps();
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, UserEvent.class.getName());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(UserEvent.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserEvent> userEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userEventConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    // Consumer для MovieEvent
    @Bean
    public ConsumerFactory<String, MovieEvent> movieEventConsumerFactory() {
        Map<String, Object> props = getProps();
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, MovieEvent.class.getName());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(MovieEvent.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MovieEvent> movieEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MovieEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(movieEventConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    // Consumer для PaymentEvent
    @Bean
    public ConsumerFactory<String, PaymentEvent> paymentEventConsumerFactory() {
        Map<String, Object> props = getProps();
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, PaymentEvent.class.getName());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(PaymentEvent.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentEvent> paymentEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentEventConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    private Map<String, Object> getProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.eventsservice.model");
        return props;
    }
}