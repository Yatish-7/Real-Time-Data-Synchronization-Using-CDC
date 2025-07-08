package com.newmeksolutions.model.pos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "POS_KPHB", uniqueConstraints = {
        @UniqueConstraint(columnNames = "PhoneNumber", name = "UQ_POS_KPHB_PhoneNumber"),
        @UniqueConstraint(columnNames = "Email", name = "UQ_POS_KPHB_Email")
})
public class POSKPHB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerID;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String location = "KPHB";

    @Column(nullable = false)
    private LocalDateTime lastPurchase = LocalDateTime.now();
}