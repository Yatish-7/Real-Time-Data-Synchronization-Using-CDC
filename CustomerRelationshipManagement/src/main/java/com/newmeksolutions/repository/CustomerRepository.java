package com.newmeksolutions.repository;

import com.newmeksolutions.model.CustomerChangePayload;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByPhoneOrEmail(String phone, String email) {
        String sql = "SELECT COUNT(*) FROM Customer_Table WHERE PhoneNumber = ? OR Email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, phone, email);
        return count != null && count > 0;
    }

    public void insertCustomer(CustomerChangePayload payload) {
        String sql = "INSERT INTO Customer_Table (CustomerID, FirstName, LastName, CustomerName, PhoneNumber, Email, Address, Location, State, PinCode, DOB, LastPurchase, Reference) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                payload.getCustomerId(),
                payload.getFirstName(),
                payload.getLastName(),
                payload.getCustomerName(),
                payload.getPhoneNumber(),
                payload.getEmail(),
                payload.getAddress(),
                payload.getLocation(),
                payload.getState(),
                payload.getPinCode(),
                payload.getDob(),
                payload.getLastPurchase(),
                payload.getReference());
    }

    public void updateCustomer(CustomerChangePayload payload) {
        String sql = "UPDATE Customer_Table SET FirstName=?, LastName=?, PhoneNumber=?, Email=?, Address=?, Location=?, State=?, PinCode=?, DOB=?, LastPurchase=?, Reference=?, CustomerName=? WHERE CustomerID=?";
        jdbcTemplate.update(sql,
                payload.getFirstName(),
                payload.getLastName(),
                payload.getPhoneNumber(),
                payload.getEmail(),
                payload.getAddress(),
                payload.getLocation(),
                payload.getState(),
                payload.getPinCode(),
                payload.getDob(),
                payload.getLastPurchase(),
                payload.getReference(),
                payload.getCustomerName(),
                payload.getCustomerId());
    }

    public Optional<CustomerChangePayload> findByPhoneOrEmail(String phone, String email) {
        String sql = "SELECT * FROM Customer_Table WHERE PhoneNumber = ? OR Email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapRow, phone, email));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void deleteCustomerByCrmId(int crmId) {
        String sql = "DELETE FROM Customer_Table WHERE CRMID = ?";
        jdbcTemplate.update(sql, crmId);
    }

    private CustomerChangePayload mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
        CustomerChangePayload c = new CustomerChangePayload();
        c.setCrmId(rs.getInt("CRMID"));
        c.setCustomerId(rs.getInt("CustomerID"));
        c.setFirstName(rs.getString("FirstName"));
        c.setLastName(rs.getString("LastName"));
        c.setCustomerName(rs.getString("CustomerName"));
        c.setPhoneNumber(rs.getString("PhoneNumber"));
        c.setEmail(rs.getString("Email"));
        c.setAddress(rs.getString("Address"));
        c.setLocation(rs.getString("Location"));
        c.setState(rs.getString("State"));
        c.setPinCode(rs.getString("PinCode"));
        c.setDob(rs.getDate("DOB"));
        c.setLastPurchase(rs.getTimestamp("LastPurchase"));
        c.setReference(rs.getString("Reference"));
        return c;
    }
}
