package com.example.kafkademo.producer;

import com.example.kafkademo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topicName;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate,
                                @Value("${app.kafka.topic}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendMessage(String message) {
        log.info("Sending String message to topic '{}': {}", topicName, message);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> handleResult("String", message, result, ex));
    }

    public void sendUser(User user) {
        log.info("Sending User JSON to topic '{}': {}", topicName, user);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, user);
        future.whenComplete((result, ex) -> handleResult("User", user, result, ex));
    }

    private void handleResult(String type,
                              Object payload,
                              SendResult<String, Object> result,
                              Throwable ex) {
        if (ex != null) {
            log.error("Failed to send {} payload to Kafka: {}", type, payload, ex);
            return;
        }

        if (result != null && result.getRecordMetadata() != null) {
            log.info("Sent {} payload successfully. topic={}, partition={}, offset={}",
                    type,
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
        }
    }
}
