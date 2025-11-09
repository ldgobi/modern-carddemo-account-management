package com.example.demo.repository;

import com.example.demo.entity.TransactionCategoryBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionCategoryBalanceRepository extends JpaRepository<TransactionCategoryBalance, Long> {
    
    List<TransactionCategoryBalance> findByAccountId(Long accountId);
    
    Optional<TransactionCategoryBalance> findByAccountIdAndTypeCodeAndCategoryCode(
            Long accountId, 
            String typeCode, 
            String categoryCode);
}
