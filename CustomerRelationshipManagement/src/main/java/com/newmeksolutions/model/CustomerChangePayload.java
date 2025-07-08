package com.newmeksolutions.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.sql.Timestamp;

public class CustomerChangePayload {

    @JsonIgnore
    private int crmId;

    @JsonProperty("customerid")
    private int customerId;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("customername")
    private String customerName;

    @JsonProperty("phonenumber")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("location")
    private String location;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pincode")
    private String pinCode;

    @JsonProperty("dob")
    private Date dob;

    @JsonProperty("lastpurchase")
    private long rawLastPurchase;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("actionType")
    private String actionType;

    @JsonProperty("loggedAt")
    private String loggedAt;

    @JsonIgnore
    public Timestamp getLastPurchase() {
        try {
            long millis = rawLastPurchase > 9999999999999L ? rawLastPurchase / 1000 : rawLastPurchase;
            return new Timestamp(millis);
        } catch (Exception e) {
            return new Timestamp(System.currentTimeMillis());
        }
    }

    @JsonIgnore
    public Timestamp getActionTime() {
        try {
            return loggedAt != null ? Timestamp.valueOf(loggedAt) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // Getters and Setters

    public int getCrmId() {
        return crmId;
    }

    public void setCrmId(int crmId) {
        this.crmId = crmId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerName() {
        if (customerName != null && !customerName.isBlank()) {
            return customerName.trim();
        }
        String fullName = ((firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "")).trim();
        return fullName.isBlank() ? null : fullName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public long getRawLastPurchase() {
        return rawLastPurchase;
    }

    public void setRawLastPurchase(long rawLastPurchase) {
        this.rawLastPurchase = rawLastPurchase;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(String loggedAt) {
        this.loggedAt = loggedAt;
    }

    public void determineReference() {
        boolean isBasicNull =
                (this.dob == null) &&
                isNullOrEmpty(this.address) &&
                isNullOrEmpty(this.firstName) &&
                isNullOrEmpty(this.lastName) &&
                isNullOrEmpty(this.pinCode) &&
                isNullOrEmpty(this.state);

        this.reference = isBasicNull ? "POS_KPHB" : "OnlineReg";
    }

    public void buildCustomerNameIfMissing() {
        if (this.customerName == null || this.customerName.isBlank()) {
            String full = ((this.firstName != null ? this.firstName : "") + " " + (this.lastName != null ? this.lastName : "")).trim();
            this.customerName = full.isBlank() ? null : full;
        }
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    @JsonIgnore
    public void setLastPurchase(Timestamp ts) {
        if (ts != null) {
            this.rawLastPurchase = ts.getTime();
        }
    }
}
