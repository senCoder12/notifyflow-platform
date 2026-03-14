
# NotifyFlow 🚀
**Scalable Event-Driven Notification System**

NotifyFlow is a **distributed event-driven notification platform** built using **Spring Boot, Apache Kafka, and Redis**. The system processes notifications asynchronously while ensuring reliability with retry pipelines, rate limiting, and Dead Letter Queues.

---

## ✨ Features

- Event-driven architecture using **Apache Kafka**
- Handles **10K+ async events/min (simulated load)**
- **Redis-based rate limiting** to prevent notification flooding
- **Retry pipelines + Dead Letter Queues (DLQ)** for failure handling
- **Real-time notification delivery status via WebSockets**
- **Horizontally scalable microservices architecture**

---
## 🔄 Notification Processing Flow

The end-to-end notification pipeline follows an **event-driven architecture** powered by Kafka.

```
Client
   │
   ▼
API Gateway
   │
   ▼
Rate Limiter Service
   │
   ▼
Notification Ingestion Service
   │
   ▼
Kafka (notification-events)
   │
   ▼
Notification Processor Service
   │
   ▼
Kafka (processed-notifications)
   │
   ▼
Notification Delivery Service
   │
   ▼
Kafka (delivery-status-events)
   │
   ▼
WebSocket Service
   │
   ▼
Real-time Client Updates
```

### Flow Explanation

1. **Client** sends a notification request.
2. **API Gateway** routes the request to backend services.
3. **Rate Limiter Service** enforces request limits using Redis.
4. **Notification Ingestion Service** validates the request and publishes it to **Kafka (`notification-events`)**.
5. **Notification Processor Service** consumes events and prepares notifications for delivery.
6. Processed notifications are published to **Kafka (`processed-notifications`)**.
7. **Notification Delivery Service** sends the notification through delivery channels.
8. Delivery status is published to **Kafka (`delivery-status-events`)**.
9. **WebSocket Service** streams delivery updates to connected clients in real time.

## ⚙️ Tech Stack

**Backend**
- Java
- Spring Boot
- Spring Kafka
- WebSockets

**Infrastructure**
- Apache Kafka
- Redis
- Docker

**Architecture**
- Microservices
- Event-driven architecture
- Asynchronous processing

---

## 📊 Performance

- Processes **10K+ notification events per minute** (simulated load)
- Reliable message processing with **Kafka retries and DLQ**
- Supports **thousands of concurrent WebSocket connections**

---

## 🚀 Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/notifyflow.git
cd notifyflow
```

### 2. Start dependencies

Start Kafka and Redis using Docker.

```bash
docker-compose up -d
```

### 3. Run services

```bash
mvn spring-boot:run
```

---

## 📬 Example Notification Event

```json
{
  "userId": "12345",
  "type": "EMAIL",
  "message": "Your order has been shipped",
  "timestamp": "2026-03-14T10:30:00Z"
}
```

---

## ⚙️ Kafka Topic & Partition Design

The system uses multiple Kafka topics to decouple services and enable asynchronous event-driven processing.

### Topics Overview
| Topic Name                      | Produced By                    | Consumed By                       | Purpose                                                               |
| ------------------------------- | ------------------------------ | --------------------------------- | --------------------------------------------------------------------- |
| `notification-events`           | Notification Ingestion Service | Notification Processor Service    | Stores incoming notification requests received from API               |
| `processed-notifications`       | Notification Processor Service | Notification Delivery Service     | Contains notifications after business processing and enrichment       |
| `delivery-status-events`        | Notification Delivery Service  | WebSocket Service                 | Publishes delivery results (DELIVERED / FAILED) for real-time clients |
| `processed-notifications-retry` | Notification Delivery Service  | Notification Delivery Service     | Stores temporarily failed notifications to be retried                 |
| `processed-notifications-dlq`   | Notification Delivery Service  | Monitoring / DLQ inspection tools | Stores permanently failed notifications after max retries             |


---

### Partition Strategy

Kafka topics are partitioned using **userId** as the partition key.

**Reasons:**

- Ensures **ordering of notifications per user**
- Enables **parallel processing across partitions**
- Supports **horizontal scaling with consumer groups**

Example:

```
Partition Key: userId
Topic: notification-events

Partition 0 → userId: 101, 204  
Partition 1 → userId: 102, 305  
Partition 2 → userId: 103, 410
```

---

### Consumer Groups

Each processing stage uses **consumer groups** to scale horizontally.

| Service | Consumer Group |
|-------|--------|
| Notification Processor | `notification-processor-group` |
| Notification Delivery | `notification-delivery-group` |
| WebSocket Service | `websocket-updates-group` |

Multiple instances of each service can consume events in parallel, increasing throughput.

---

### Reliability Strategy

To ensure reliable processing:

- **Notification Delivery Service handles retries** for transient failures.
- Failed events are pushed to the **`retry-notifications` topic** and reprocessed.
- Events that exceed retry limits are sent to **Dead Letter Queue (`dead-letter-notifications`)**.
- **At-least-once delivery** is ensured using Kafka consumer offset commits.

---

### Scalability

With Kafka partitioning and consumer groups, the system can scale to handle:

- **10,000+ notification events per minute**
- **Horizontal scaling of processor and delivery services**
- **Real-time event streaming to thousands of WebSocket clients**

---

---

## 🧠 Key Engineering Concepts

- Event-driven microservices architecture
- Kafka consumer groups & partitioning
- Distributed rate limiting using Redis
- Retry pipelines & Dead Letter Queues
- Real-time event streaming via WebSockets

---

## 🔮 Future Improvements

- Multi-channel notifications (Email, SMS, Push)
- Observability with **Prometheus + Grafana**
- Distributed tracing with **OpenTelemetry**
- Kubernetes deployment

---

⭐ If you found this project useful, consider starring the repository.