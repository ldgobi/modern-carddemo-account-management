package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "disclosure_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisclosureGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_group_id", nullable = false, length = 10)
    private String accountGroupId;
    
    @Column(name = "transaction_category_code", nullable = false, length = 4)
    private String transactionCategoryCode;
    
    @Column(name = "transaction_type_code", nullable = false, length = 2)
    private String transactionTypeCode;
    
    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public DisclosureGroup(String accountGroupId, String transactionCategoryCode, String transactionTypeCode, BigDecimal interestRate) {
        this.accountGroupId = accountGroupId;
        this.transactionCategoryCode = transactionCategoryCode;
        this.transactionTypeCode = transactionTypeCode;
        this.interestRate = interestRate;
    }
}
