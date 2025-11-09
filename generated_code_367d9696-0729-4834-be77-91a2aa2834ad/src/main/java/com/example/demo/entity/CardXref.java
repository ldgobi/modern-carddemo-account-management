package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "card_xref", indexes = {
    @Index(name = "idx_account_id", columnList = "account_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardXref {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "card_number", nullable = false, length = 16)
    private String cardNumber;
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public CardXref(String cardNumber, Long customerId, Long accountId) {
        this.cardNumber = cardNumber;
        this.customerId = customerId;
        this.accountId = accountId;
    }
}
