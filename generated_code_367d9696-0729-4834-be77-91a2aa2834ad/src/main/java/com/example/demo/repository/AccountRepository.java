package com.example.demo.repository;

import com.example.demo.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountId(Long accountId);
    
    List<Account> findByCustomerId(Long customerId);
    
    Page<Account> findByCustomerId(Long customerId, Pageable pageable);
    
    List<Account> findByActiveStatus(String activeStatus);
    
    Page<Account> findByActiveStatus(String activeStatus, Pageable pageable);
    
    List<Account> findByGroupId(String groupId);
    
    Page<Account> findByGroupId(String groupId, Pageable pageable);
}
