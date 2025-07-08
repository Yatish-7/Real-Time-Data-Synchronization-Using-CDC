
# Real-Time-Data-Synchronization-Using-CDC
![Logo](Logo.png)  
![Java Version](https://img.shields.io/badge/Java-17+-blue.svg)  
![Maven Build](https://img.shields.io/badge/Build-Maven-success.svg)  
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-orange.svg)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17+-blue.svg)  

---

## 🚀 About The Project

**Real-Time-Data-Synchronization-Using-CDC** is a high-performance, event-driven Java-based solution designed to **capture and synchronize customer data** in real time using **Change Data Capture (CDC)** — not polling.

It monitors source databases like POS systems and e-commerce platforms using **WAL (Write-Ahead Logging)** and **Debezium** to stream changes, ensuring instant, reliable, and low-latency data flow through **RabbitMQ** to a target system like **PostgreSQL**.

---

## 🧠 Why CDC and Not Polling?

> Traditional polling is inefficient, delayed, and resource-heavy.  
> This project leverages **CDC via Debezium**, enabling **real-time and low-overhead synchronization** by listening directly to **database change logs** (WAL in PostgreSQL).

---

## 🛠️ Key Features

- ✅ Real-Time CDC-Based Syncing — Captures inserts, updates, and deletes instantly  
- ✅ Debezium Embedded Engine — Lightweight CDC with no Kafka dependency  
- ✅ RabbitMQ — Asynchronous, scalable message broker  
- ✅ PostgreSQL WAL Integration — Efficient database-level change tracking  
- ✅ JSON Payload Messaging — Clean, standardized data format  
- ✅ Microservices Architecture — Clear separation of CDC producer and consumer

---

## 🏗️ Built With

| Technology        | Purpose                                      |
|------------------|----------------------------------------------|
| Java 17           | Backend development                          |
| Spring Boot       | Microservice framework                       |
| Debezium          | CDC engine (WAL-based)                       |
| RabbitMQ          | Event-driven messaging platform              |
| PostgreSQL        | WAL-enabled source & target DB               |
| MS SQL Server     | Source database for POS/eCommerce            |
| Maven             | Build and dependency management              |

---

## ⚙️ How It Works

1. PostgreSQL and MS SQL Server act as source systems.
2. Debezium Embedded Engine captures row-level changes (INSERT/UPDATE/DELETE) using WAL logs.
3. Changes are transformed into JSON messages.
4. These messages are published to RabbitMQ queues.
5. The Spring Boot Consumer service listens to the queue and stores the data in the PostgreSQL consolidated database.

---

## 📦 Installation & Setup

### 1. Clone the Repository
```
git clone https://github.com/Yatish-7/Real-Time-Data-Synchronization-Using-CDC.git
cd Real-Time-Data-Synchronization-Using-CDC
```

### 2. Configure Properties

Update the following configuration files:

- Database Credentials in `application.properties` or `application.yml`
- RabbitMQ Configuration in `RabbitMQConfig.java` or `application.properties`
- Debezium Connector Configuration in `DebeziumConnectorConfig.java` (PostgreSQL/MS SQL JDBC URLs, tables)

### 3. Build & Run Services

#### Producer (Debezium CDC → RabbitMQ Publisher)
```
cd ProducerService
mvn clean install
java -jar target/ProducerService-1.0.jar
```

#### Consumer (RabbitMQ Listener → PostgreSQL Writer)
```
cd ConsumerService
mvn clean install
java -jar target/ConsumerService-1.0.jar
```

---

## 🧪 Usage Tips

- Add table-specific filters in Debezium config.
- Customize payload transformation logic.
- Add DLQ or retry handlers in RabbitMQ for robustness.

---

## 🤝 Contributing

We welcome and appreciate contributions to make this project better!

1. Fork the Repository  
2. Create a Feature Branch  
```
git checkout -b feature/YourFeature
```
3. Make and Commit Your Changes  
```
git commit -m "Add YourFeature"
```
4. Push to Your Branch  
```
git push origin feature/YourFeature
```
5. Open a Pull Request

---

## 📞 Contact

- GitHub Repository: https://github.com/Yatish-7/Real-Time-Data-Synchronization-Using-CDC  
- Email: mailtoyatish55@gmail.com

---


## 📚 Acknowledgments

- [Debezium](https://debezium.io/) — CDC connector for relational databases  
- [RabbitMQ](https://www.rabbitmq.com/) — Reliable messaging infrastructure  
- [PostgreSQL](https://www.postgresql.org/) — WAL-based relational database  
- [MS SQL Server](https://www.microsoft.com/en-us/sql-server) — POS/eCommerce data source  
- [Java](https://www.java.com/) & [Spring Boot](https://spring.io/projects/spring-boot) — Microservice foundation  
- [Open Source Community](https://opensource.org/) — Thank you for libraries and support

---

## 👥 Authors

- Yatish Datta — B.Tech Student, Koneru Lakshmaiah Deemed to be University  

---

## 🔗 Connect with Us

- [Yatish Datta](https://www.linkedin.com/in/yatishdatta/)

---
