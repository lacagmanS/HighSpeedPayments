# Project: High-Frequency Payments Dashboard

### A full-stack, real-time simulation of a low-latency financial transaction engine built with Java, React, and the LMAX Disruptor.

This project is an end-to-end implementation of a high-performance payment processing system, designed to emulate the demanding environments of FinTech and modern banking. At its core is a powerful Java backend engineered for speed and reliability, coupled with a sleek, interactive React dashboard for real-time monitoring and control.

---
## Key Features

* **High-Performance Java Backend**: The core is built on the **LMAX Disruptor**, a lock-free, asynchronous pattern that enables massive throughput with predictable, low latency by minimizing garbage collection and leveraging mechanical sympathy.
* **Real-Time React Dashboard**: The UI is a dynamic Single-Page Application built with **React** and **MUI**. It provides a live, "mission control" view of the backend engine, with all data pushed instantly from the server via **WebSockets**.
* **Cryptographic Integrity**: Every transaction is cryptographically signed using **RSA** upon completion. The dashboard allows for on-demand verification, guaranteeing the authenticity and integrity of the historical transaction records.
* **Interactive Controls & Performance Testing**: The dashboard isn't just for viewing; it's a control panel. Users can dynamically create accounts, submit payments, and trigger a "Chaos Button" to unleash a high-volume burst of transactions, visually demonstrating the system's stability under load.

---
## The Dashboard Explained

The user interface is a data-rich command center for the payment processor, providing both real-time monitoring and interactive controls.

### 1. Live Performance Graph
The main chart, powered by **Recharts**, visualizes the system's live **Transactions Per Second (TPS)**, providing immediate feedback on the engine's throughput.

![Live Performance Graph](Projects\payment-ui\public\livetransactions.jpg)

### 2. Control Panel
The control panel allows for full interaction with the system. Users can dynamically create new accounts and submit payments between them using interactive forms.

![Control Panel for Account Creation and Payments](payment-ui\public\SendingPayments.jpg)

### 3. Performance Stress Test
The "Chaos Button" unleashes a high-volume burst of 500 transactions to demonstrate the system's stability and high-throughput capabilities under load.

![Performance Test in Action](payment-ui\public\500paymenttest.jpg)

### 4. Live Feed & Transaction History
A tabbed panel provides two views of transaction data. The **Live Feed** shows raw journal messages pushed via WebSockets the moment they are processed, while the **History** tab provides a structured log of all completed and failed transactions.

![Live Feed and Transaction History Panel](payment-ui\public\transactionhistory.jpg)

### 5. Transaction Invoice & Verification
Clicking any transaction in the history opens a detailed invoice. This includes the unique **RSA digital signature**. The "Verify Authenticity" button makes a call to the backend to cryptographically confirm that the transaction data has not been tampered with.

![Transaction Invoice with Signature Verification](payment-ui\public\verifyauthentic.jpg)

---
## Tech Stack

| Area      | Technology                                                              |
| :-------- | :---------------------------------------------------------------------- |
| **Backend** | Java 17, Spring Boot 3, LMAX Disruptor, Spring WebSockets, Maven        |
| **Frontend**| React, MUI (Material-UI), Axios, Recharts, StompJS, SockJS             |
| **Tooling** | Git, VS Code                                                            |

---
## System Architecture

1.  The **React Client** submits a payment or account creation request to the **Spring Boot REST API**.
2.  For payments, the Controller immediately returns `202 Accepted` and passes the request to a **Publisher**.
3.  The Publisher places the transaction onto the **LMAX Disruptor RingBuffer**.
4.  A chain of **Event Handlers** processes the event asynchronously:
    * `JournalingHandler` → `ValidationHandler` → `SettlementHandler` → `ClearingHandler`
5.  Handlers push real-time updates (logs, metrics) via **WebSockets** back to the React UI.
6.  The `SettlementHandler` cryptographically signs and stores the final transaction record in an in-memory history service.
