package com.example.kafkademo.controller;

import com.example.kafkademo.model.User;
import com.example.kafkademo.producer.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class KafkaMessageController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaMessageController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam("message") String message) {
        kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok("String message sent to Kafka topic successfully.");
    }

    @PostMapping("/publish/user")
    public ResponseEntity<String> publishUser(@RequestBody User user) {
        kafkaProducerService.sendUser(user);
        return ResponseEntity.ok("User JSON message sent to Kafka topic successfully.");
    }
}
