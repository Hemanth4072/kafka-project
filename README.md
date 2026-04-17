# Spring Boot Kafka Producer/Consumer Demo

This project demonstrates end-to-end messaging with **Spring Boot + Apache Kafka**:

**REST API** → **Kafka Producer** → **Kafka Topic (`demo-topic`)** → **Kafka Consumer** → **Console Logs**

## Tech stack
- Java 17+
- Spring Boot 3
- Spring Kafka
- Maven

## Project structure

```text
src/main/java/com/example/kafkademo
├── KafkaDemoApplication.java
├── config
│   └── KafkaTopicConfig.java
├── consumer
│   └── KafkaConsumerService.java
├── controller
│   └── KafkaMessageController.java
├── model
│   └── User.java
└── producer
    └── KafkaProducerService.java
```

## Run instructions (step-by-step)

### 1) Start Kafka on localhost:9092
If you use Docker:

```bash
docker run -d --name kafka -p 9092:9092 apache/kafka:latest
```

> Any Kafka setup is fine as long as broker is reachable at `localhost:9092`.

### 2) Build the Spring Boot app
```bash
mvn clean package
```

### 3) Run the app
```bash
mvn spring-boot:run
```

### 4) Publish a plain string message
```bash
curl -X POST "http://localhost:8080/api/publish?message=hello"
```

### 5) Publish a JSON `User` message
```bash
curl -X POST "http://localhost:8080/api/publish/user" \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"Alice"}'
```

### 6) Verify consumer output in console
You should see log lines similar to:

```text
Received message from Kafka: "hello"
Received message from Kafka: {"id":1,"name":"Alice"}
```

## API summary
- `POST /api/publish?message=hello` → sends String message
- `POST /api/publish/user` with JSON body → sends `User` JSON message

## Configuration
Kafka properties are in `src/main/resources/application.yml`:
- bootstrap servers: `localhost:9092`
- consumer group-id: `demo-group`
- serializers/deserializers configured for producer/consumer
- topic name: `demo-topic`
