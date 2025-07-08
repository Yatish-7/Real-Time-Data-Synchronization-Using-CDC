package com.newmeksolutions.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.newmeksolutions.util.JSONUtil;
import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DebeziumChangePublisher {

    @Autowired
    private Configuration posDBConfig;

    @Autowired
    private Configuration ecommerceDBConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JSONUtil jsonUtil;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private DebeziumEngine<ChangeEvent<String, String>> posEngine;
    private DebeziumEngine<ChangeEvent<String, String>> ecommerceEngine;

    @PostConstruct
    public void startEngines() 
    {
        posEngine = DebeziumEngine.create(Json.class)
                .using(posDBConfig.asProperties())
                .notifying(event -> handleChange(event, "PointOfSale"))
                .build();

        ecommerceEngine = DebeziumEngine.create(Json.class)
                .using(ecommerceDBConfig.asProperties())
                .notifying(event -> handleChange(event, "EcommerceDB"))
                .build();

        executor.execute(posEngine);
        executor.execute(ecommerceEngine);
        System.out.println("🚀 Debezium CDC Engines started.");
    }

    private void handleChange(ChangeEvent<String, String> event, String source)
    {
        try {
            String value = event.value();
            if (value == null || value.trim().isEmpty()) return;

            JsonNode payload = jsonUtil.parsePayload(value);
            if (payload == null) return;

            String op = payload.get("op").asText(); // c, u, d
            String actionType = switch (op) {
                case "c" -> "INSERT";
                case "u" -> "UPDATE";
                case "d" -> "DELETE";
                default -> "UNKNOWN";
            };

            String emoji = switch (op) {
                case "c" -> "🟢";
                case "u" -> "🟡";
                case "d" -> "🔴";
                default -> "❓";
            };

            JsonNode dataNode = switch (op) {
                case "c", "u" -> payload.get("after");
                case "d" -> payload.get("before");
                default -> null;
            };

            if (dataNode == null || dataNode.isNull()) return;

            // Inject actionType and loggedAt
            if (dataNode.isObject()) {
                ((com.fasterxml.jackson.databind.node.ObjectNode) dataNode)
                    .put("actionType", actionType)
                    .put("loggedAt", java.time.LocalDateTime.now().format(
                            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
            }

            String finalJson = jsonUtil.prettyPrint(dataNode);

            // 🔔 Log exactly as requested
            System.out.println("[" + source + "] " + emoji + " " + actionType + " event detected:");
            System.out.println("📄 Changed Data:\n" + finalJson);

            rabbitTemplate.convertAndSend("POS_Exchange(CDC)", "POS_Key", dataNode);
            System.out.println("📤 Data pushed to RabbitMQ (POS_Data_Queue(CDC))");

        } catch (Exception e) 
        {
            System.err.println("❌ Error handling change event: " + e.getMessage());
        }
    }
}
