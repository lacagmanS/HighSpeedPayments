High-Speed Payment Processor
A low-latency, high-throughput financial transaction processing engine built with Java, Spring Boot, and the LMAX Disruptor.
This project is a backend system designed to simulate the core of a modern payment processor or financial exchange. It is engineered from the ground up to handle a massive volume of transactions with predictable, low latency by leveraging an asynchronous, event-driven architecture. The core of the system is the LMAX Disruptor, a high-performance inter-thread messaging library that enables lock-free concurrency.

Table of Contents

About The Project

Core Concepts Demonstrated

Tech Stack

Getting Started

Usage & API Endpoint

Project Structure

Potential Enhancements

License

About The Project
In many high-performance systems, especially in finance, the primary challenge is not just processing data quickly, but doing so with predictable latency. Common JVM-based applications often suffer from "jitter" caused by garbage collection (GC) pauses and thread lock contention. This project tackles these problems head-on.

The goal was to build a system that avoids these pitfalls by using modern, performance-oriented design patterns. The application accepts payment requests via a REST API and processes them asynchronously on a multi-stage pipeline, ensuring the API endpoint remains highly responsive regardless of the backend load.

This serves as a practical exploration into the principles of "Mechanical Sympathy"—designing software that works in harmony with the underlying hardware—to build truly high-performance systems.

Core Concepts Demonstrated
Low-Latency & High-Throughput Design: Architecture focused on minimising response times and maximising the number of transactions processed per second.

Asynchronous & Event-Driven Architecture: Decoupling the web-facing API from the core processing logic using an in-memory message queue.

Lock-Free Concurrency: Utilising the LMAX Disruptor pattern to enable multiple threads to communicate without using costly locks.

Mechanical Sympathy & GC Tuning: The entire system is built around the principle of object reuse to eliminate memory allocation (and thus garbage collection) on the critical processing path.

Multi-Stage Data Processing Pipeline: A clear, directed acyclic graph (DAG) of event handlers ensures that each stage of payment processing (e.g., Journaling, Validation) happens in a specific, reliable order.

Clean API Design: A simple and clear RESTful API for submitting transactions.

Tech Stack
Backend:

Concurrency: LMAX Disruptor 4.0.0

Build & Dependencies:

API: Spring Web (REST)

Version Control:

Getting Started
To get a local copy up and running, follow these simple steps.

Prerequisites
JDK 17 or later

Apache Maven

An IDE like IntelliJ IDEA

Installation & Setup
Clone the repository

Bash

git clone https://github.com/your-username/highspeed-payments.git
Navigate to the project directory

Bash

cd highspeed-payments
Build the project with Maven
This will download all the necessary dependencies.

Bash

mvn clean install
Run the application
You can run the main application class HighspeedPaymentsApplication.java directly from your IDE.

The application will start, and the REST API will be available on http://localhost:8080.

Usage & API Endpoint
The primary way to interact with the application is by sending a POST request to the /api/payments endpoint.

It is highly recommended to use the IntelliJ HTTP Client for testing to avoid command-line quoting issues.

In IntelliJ, create a new Scratch File of type HTTP Request.

Paste the following content and click the green "play" icon next to the POST line.

HTTP

### Submit a valid payment for processing
POST http://localhost:8080/api/payments
Content-Type: application/json

{
  "sourceAccountId": "ACC_SRC_001",
  "destinationAccountId": "ACC_DEST_002",
  "amount": 250.50
}
Expected Success Response
The API will immediately respond with a 202 Accepted status, acknowledging that the request has been accepted for asynchronous processing.

Status Code: 202 Accepted

Body:

JSON

{
  "message": "Payment request accepted for processing.",
  "transactionId": "unique-uuid-will-be-here"
}
Check the application console in your IDE to see the log output from the JournalingHandler.

Project Structure
The project is organised into logical packages based on their function:

com.paymentsprocessor.highspeedpayments

config: Contains the central DisruptorConfig class responsible for wiring and launching the system.

controller: Holds the Spring RestController for the public-facing API.

disruptor: The core high-performance components.

event: The PaymentEvent data carrier object.

factory: The PaymentEventFactory for pre-allocating events.

handler: The business logic handlers (JournalingHandler, ValidationHandler, etc.).

publisher: The PaymentEventPublisher which provides a clean interface to the RingBuffer.

domain: Contains Data Transfer Objects (DTOs) like PaymentRequest.

service: Contains higher-level services (PaymentService, LoggingService).

Potential Enhancements
Implement a SettlementHandler: Add a handler to simulate the actual debit/credit of funds from in-memory accounts.

Add a Status Query Endpoint: Implement a GET /api/payments/{transactionId} endpoint to allow clients to check the final status of a transaction.

Performance Benchmarking: Use the Java Microbenchmark Harness (JMH) to create benchmarks that measure the system's throughput in events per second.

Write Unit & Integration Tests: Add comprehensive tests using JUnit and Mockito to ensure the reliability of each component.

License
Distributed under the MIT License. See LICENSE for more information.







