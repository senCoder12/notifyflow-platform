#!/bin/bash

# Start Docker containers (Redies, Kafka, Zookeeper)
echo "Starting infrastructure..."
docker-compose up -d

# Array of services
services=(
  "api-gateway"
  "rate-limiter-service"
  "notification-ingestion-service"
  "notification-processor-service"
  "notification-delivery-service"
  "websocket-service"
)

# Start each service
for service in "${services[@]}"; do
  echo "Starting $service..."
  cd "$service"
  # Run in background and redirect output to a log file
  nohup mvn spring-boot:run > spring-boot.log 2>&1 &
  cd ..
done

echo "All services started in background. Check individual log files for status."
