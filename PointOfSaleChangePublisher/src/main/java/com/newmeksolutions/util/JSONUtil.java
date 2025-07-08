package com.newmeksolutions.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JSONUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode parsePayload(String value) {
        try {
            return objectMapper.readTree(value).get("payload");
        } catch (Exception e) {
            System.err.println("❌ Error parsing payload: " + e.getMessage());
            return null;
        }
    }

    public String prettyPrint(JsonNode node) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (Exception e) {
            System.err.println("❌ Error converting node to string: " + e.getMessage());
            return null;
        }
    }
}
