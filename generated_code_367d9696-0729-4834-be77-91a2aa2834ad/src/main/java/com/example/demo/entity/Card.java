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
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    
    @Id
    @Column(name = "card_number", length = 16, nullable = false)
    private String cardNumber;
    
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "card_status", length = 1, nullable = false)
    private String cardStatus;
    
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public Card(String cardNumber, Long accountId, Long customerId, String cardStatus, LocalDate expirationDate) {
        this.cardNumber = cardNumber;
        this.accountId = accountId;
        this.customerId = customerId;
        this.cardStatus = cardStatus;
        this.expirationDate = expirationDate;
    }
}
