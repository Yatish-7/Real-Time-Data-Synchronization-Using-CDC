# Real-Time-Data-Synchronization-Using-CDC
![Logo](Logo.png)  
![Java Version](https://img.shields.io/badge/Java-17+-blue.svg)  
![Maven Build](https://img.shields.io/badge/Build-Maven-success.svg)  
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-orange.svg)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17+-blue.svg)  

---

## 🚀 About The Project

**Real-Time-Data-Synchronization-Using-CDC** is a high-performance, event-driven Java-based solution designed to **capture and synchronize customer data** in real time using **Change Data Capture (CDC)** — not polling.

It monitors source databases like POS systems and e-commerce platforms using **WAL (Write-Ahead Logging)** and Debezium to stream changes, ensuring instant, reliable, and low-latency data flow through **RabbitMQ** to a target system like **PostgreSQL**.

---

## 🧠 Why CDC and Not Polling?

> Traditional polling is inefficient, delayed, and resource-heavy.  
> This project leverages **CDC via Debezium**, enabling **real-time and low-overhead synchronization** by listening to the **actual database change logs** (WAL in PostgreSQL).

---

## 🛠️ Key Features

- ✅ **Real-Time CDC-Based Syncing** — Instantly captures and transmits inserts, updates, and deletes  
- ✅ **Debezium Embedded Engine** — No Kafka setup required  
- ✅ **RabbitMQ Message Queue** — Asynchronous, fault-tolerant messaging  
- ✅ **PostgreSQL WAL Capture** — Efficient and accurate data change tracking  
- ✅ **JSON Payload Processing** — Structured and standardized messages  
- ✅ **Modular Microservices** — Clean separation between producer (CDC) and consumer (sync handler)

---

## 🏗️ Built With

| Technology        | Purpose                                      |
|-------------------|----------------------------------------------|
| **Java 17**       | Backend services                             |
| **Spring Boot**   | Fast, configurable microservices             |
| **Debezium**      | WAL-based CDC connector                      |
| **RabbitMQ**      | High-throughput message broker               |
| **PostgreSQL**    | WAL-enabled source/target database           |
| **MS SQL Server** | Source database for capturing business data  |
| **Maven**         | Build and dependency management              |

---

## ⚙️ How It Works

1. **PostgreSQL** and **MS SQL Server** act as **source systems**.
2. **Debezium Embedded Engine** captures changes (INSERT, UPDATE, DELETE) via **WAL logs**.
3. Changes are formatted into **JSON messages** and published to **RabbitMQ**.
4. The **Spring Boot consumer** service receives the messages from RabbitMQ.
5. The consumer stores the data in the **final PostgreSQL consolidated database** or **updates accordingly**.

---

## 📦 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/Yatish-7/Real-Time-Data-Synchronization-Using-CDC.git
cd Real-Time-Data-Synchronization-Using-CDC

2. Configure Properties
Update database credentials in:

application.properties or application.yml

Set RabbitMQ configs in:

RabbitMQConfig.java or config file

Define connector configurations in:

DebeziumConnectorConfig.java (use PostgreSQL and MS SQL JDBC URLs)


3. Build & Run Services
Producer (Debezium CDC + RabbitMQ Publisher)

bash
Copy
Edit
cd ProducerService
mvn clean install
java -jar target/ProducerService-1.0.jar
Consumer (RabbitMQ Listener + DB Writer)

bash
Copy
Edit
cd ConsumerService
mvn clean install
java -jar target/ConsumerService-1.0.jar



🧪 Usage Tips
Add table-specific configurations in Debezium connector.

Customize payloads or message converters for downstream flexibility.

Consumer can be scaled independently for high throughput.


## 📞 Contact
Project Link: [GitHub Repository](https://github.com/Yatish-7/Real-Time-Data-Synchronization-Using-CDC)  
Email: [thardhikreddy16@gmail.com](mailto:thardhikreddy16@gmail.com)

## 📚 Acknowledgments
- [PostgreSQL](https://www.postgresql.org/) — Reliable relational database for consolidated data storage  
- [MS SQL Server](https://www.microsoft.com/en-us/sql-server) — Supported source database for extracting POS and online registration data  
- [RabbitMQ](https://www.rabbitmq.com/) — High‑performance message broker for asynchronous communication  
- [Java](https://www.java.com/) & [Maven](https://maven.apache.org/) — The programming language and build tool that form the backbone of this project  
- [Open Source Contributors](https://opensource.org/) — Thanks to the open‑source libraries and communities that made this project possible  



## 🤝 Contributing
We welcome and appreciate contributions to make this project better! Here’s how you can get involved:

1. **Fork the Repository**  
   Click the **Fork** button in the top right corner of the GitHub page.

2. **Create a Feature Branch**  
    ```
    git checkout -b feature/AmazingFeature
    ```

3. **Make Your Changes**  
   Add your improvements or bug fixes.

4. **Commit Your Changes**  
    ```
    git commit -m "Add AmazingFeature"
    ```

5. **Push to Your Branch**  
    ```
    git push origin feature/AmazingFeature
    ```

6. **Open a Pull Request**  
   Submit a Pull Request to have your changes reviewed and merged.

---



## 👥 Authors
Developed by:
- **Yatish Datta** — B.Tech Student, Koneru Lakshmaiah Deemed to be University  
- **RAGHAVA NITHYANAND GUTTULA** — B.Tech Student, GITAM University
-  **Rohith Kothapalli** — B.Tech Student, GITAM University
-  **Nadimpalli Aditi Varma** — B.Tech Student, GITAM University
-  **Vennela N** — B.Tech Student, GITAM University
-  **Thumu Venkat Hardhik Reddy** — B.Tech Student, GITAM University



## 🔗 Connect with Us
- [Yatish Datta](https://www.linkedin.com/in/yatishdatta/)
- [RAGHAVA NITHYANAND GUTTULA](https://www.linkedin.com/in/raghava-nithyanand-guttula/)
- [Rohith Kothapalli](https://www.linkedin.com/in/rohith-kothapalli-46538a36a/)
- [Nadimpalli Aditi Varma](https://www.linkedin.com/in/nadimpalli-aditi-varma-28ab5627b/)
- [Vennela N](https://www.linkedin.com/in/vennela-n-117594357/)
- [Thumu Venkat Hardhik Reddy](https://www.linkedin.com/in/thumu-venkat-hardhik-reddy-596298330/)




