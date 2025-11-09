package com.example.demo.repository;

import com.example.demo.entity.DisclosureGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DisclosureGroupRepository extends JpaRepository<DisclosureGroup, Long> {
    
    Optional<DisclosureGroup> findByAccountGroupIdAndTransactionCategoryCodeAndTransactionTypeCode(
            String accountGroupId, 
            String transactionCategoryCode, 
            String transactionTypeCode);
    
    List<DisclosureGroup> findByAccountGroupId(String accountGroupId);
}
