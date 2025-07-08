package com.newmeksolutions.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newmeksolutions.model.CustomerChangePayload;
import com.newmeksolutions.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void insertCustomerIfNotExists(CustomerChangePayload payload) {
        try {
            System.out.println("\uD83D\uDCE4 Received INSERT payload:");
            System.out.println("\uD83D\uDCDD " + objectMapper.writeValueAsString(payload));

            if (payload.getPhoneNumber() == null || payload.getPhoneNumber().isBlank()) {
                System.err.println("❌ Cannot insert customer — phone number is missing.");
                return;
            }

            payload.determineReference();
            payload.buildCustomerNameIfMissing();

            if (payload.getLastPurchase() == null) {
                payload.setLastPurchase(new Timestamp(System.currentTimeMillis()));
            }

            if (repository.existsByPhoneOrEmail(payload.getPhoneNumber(), payload.getEmail())) {
                System.out.println("♻️ Record with same phone/email exists. Performing update.");
                Optional<CustomerChangePayload> existing = repository.findByPhoneOrEmail(payload.getPhoneNumber(), payload.getEmail());
                existing.ifPresent(existingData -> payload.setCustomerId(existingData.getCustomerId()));
                repository.updateCustomer(payload);
            } else {
                repository.insertCustomer(payload);
                System.out.println("✅ Inserted new customer.");
            }
        } catch (Exception e) {
            System.err.println("❌ Exception during insert: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateCustomerById(CustomerChangePayload payload) {
        try {
            System.out.println("\uD83D\uDCE4 Received UPDATE payload:");
            System.out.println("\uD83D\uDCDD " + objectMapper.writeValueAsString(payload));

            payload.determineReference();
            payload.buildCustomerNameIfMissing();

            Optional<CustomerChangePayload> existing = repository.findByPhoneOrEmail(payload.getPhoneNumber(), payload.getEmail());

            if (existing.isPresent()) {
                CustomerChangePayload existingData = existing.get();
                if (!safeEquals(existingData, payload)) {
                    payload.setCustomerId(existingData.getCustomerId());
                    System.out.println("♻️ Changes detected. Updating record in Customer_Table...");
                    repository.updateCustomer(payload);
                    System.out.println("✅ Customer updated successfully.");
                } else {
                    System.out.println("✅ No data change. Skipping update.");
                }
            } else {
                System.out.println("⚠️ No match found for update. Inserting new record.");
                repository.insertCustomer(payload);
            }
        } catch (Exception e) {
            System.err.println("❌ Exception during update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteCustomerByEmailOrPhone(String email, String phone) {
        try {
            System.out.println("\uD83D\uDDD1️ Received DELETE request for Email: " + email + ", Phone: " + phone);
            Optional<CustomerChangePayload> existing = repository.findByPhoneOrEmail(phone, email);
            if (existing.isEmpty()) {
                System.out.println("⚠️ No customer found. Skipping deletion.");
                return;
            }
            int crmId = existing.get().getCrmId();
            repository.deleteCustomerByCrmId(crmId);
            System.out.println("✅ Deleted customer with CRMID: " + crmId);
        } catch (Exception e) {
            System.err.println("❌ Exception during delete: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean safeEquals(CustomerChangePayload a, CustomerChangePayload b) {
        return eq(a.getFirstName(), b.getFirstName()) &&
               eq(a.getLastName(), b.getLastName()) &&
               eq(a.getPhoneNumber(), b.getPhoneNumber()) &&
               eq(a.getEmail(), b.getEmail()) &&
               eq(a.getAddress(), b.getAddress()) &&
               eq(a.getLocation(), b.getLocation()) &&
               eq(a.getState(), b.getState()) &&
               eq(a.getPinCode(), b.getPinCode()) &&
               eq(a.getDob(), b.getDob()) &&
               eq(a.getLastPurchase(), b.getLastPurchase()) &&
               eq(a.getReference(), b.getReference());
    }

    private boolean eq(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.toString().trim().equalsIgnoreCase(b.toString().trim());
    }
}
