package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @Column(name = "customer_id", nullable = false, length = 9)
    private Long customerId;
    
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;
    
    @Column(name = "middle_name", length = 25)
    private String middleName;
    
    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;
    
    @Column(name = "ssn", nullable = false, length = 9)
    private String ssn;
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(name = "fico_score", length = 3)
    private Integer ficoScore;
    
    @Column(name = "address_line1", nullable = false, length = 50)
    private String addressLine1;
    
    @Column(name = "address_line2", length = 50)
    private String addressLine2;
    
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    
    @Column(name = "state_code", nullable = false, length = 2)
    private String stateCode;
    
    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;
    
    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;
    
    @Column(name = "phone_number1", nullable = false, length = 15)
    private String phoneNumber1;
    
    @Column(name = "phone_number2", length = 15)
    private String phoneNumber2;
    
    @Column(name = "government_issued_id", nullable = false, length = 20)
    private String governmentIssuedId;
    
    @Column(name = "eft_account_id", length = 10)
    private String eftAccountId;
    
    @Column(name = "primary_card_holder_indicator", nullable = false, length = 1)
    private String primaryCardHolderIndicator;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public String getFullName() {
        if (middleName != null && !middleName.isEmpty()) {
            return firstName + " " + middleName + " " + lastName;
        }
        return firstName + " " + lastName;
    }
    
    public boolean isPrimaryCardHolder() {
        return "Y".equalsIgnoreCase(primaryCardHolderIndicator);
    }
    
    public boolean isValidFicoScore() {
        return ficoScore != null && ficoScore >= 300 && ficoScore <= 850;
    }
}
