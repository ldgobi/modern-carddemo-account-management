package com.example.demo.repository;

import com.example.demo.entity.CardXref;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardXrefRepository extends JpaRepository<CardXref, Long> {
    
    List<CardXref> findByAccountId(Long accountId);
    
    Optional<CardXref> findByCardNumber(String cardNumber);
    
    List<CardXref> findByCustomerId(Long customerId);
}
