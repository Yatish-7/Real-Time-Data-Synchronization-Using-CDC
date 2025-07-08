package com.newmeksolutions.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.newmeksolutions.model.CustomerChangePayload;
import com.newmeksolutions.service.CustomerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(CustomerService customerService) {
        this.customerService = customerService;
        this.objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setDateFormat(new StdDateFormat());
    }

    @RabbitListener(queues = "POS_Data_Queue(CDC)")
    public void receiveMessage(CustomerChangePayload payload) {
        try {
            System.out.println("\uD83D\uDCC5 Received CDC Event for processing:");
            System.out.println("\uD83D\uDCDD Payload:\n" + objectMapper.writeValueAsString(payload));

            payload.buildCustomerNameIfMissing();

            String action = payload.getActionType();
            if (action == null || action.isBlank()) {
                System.err.println("⚠️ Missing `actionType` in payload. Skipping.");
                return;
            }

            switch (action.toUpperCase()) {
                case "INSERT" -> customerService.insertCustomerIfNotExists(payload);
                case "UPDATE" -> customerService.updateCustomerById(payload);
                case "DELETE" -> customerService.deleteCustomerByEmailOrPhone(
                        payload.getEmail(), payload.getPhoneNumber());
                default -> System.err.println("⚠️ Unknown actionType: '" + action + "'");
            }

        } catch (Exception e) {
            System.err.println("❌ Error processing RabbitMQ message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}