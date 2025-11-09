package com.example.demo.service;

import com.example.demo.dto.AccountViewResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.CardXref;
import com.example.demo.entity.Customer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CardXrefRepository;
import com.example.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountViewService {

    private final AccountRepository accountRepository;
    private final CardXrefRepository cardXrefRepository;
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public AccountViewResponseDto getAccountView(Long accountId) {
        log.info("Retrieving account view for account ID: {}", accountId);

        if (accountId == null || accountId <= 0) {
            log.error("Invalid account ID: {}", accountId);
            throw new IllegalArgumentException("Account ID must be a positive number");
        }

        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> {
                    log.error("Account not found with ID: {}", accountId);
                    return new IllegalArgumentException("Account not found with ID: " + accountId);
                });

        CardXref cardXref = cardXrefRepository.findByAccountId(accountId).stream()
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Card cross-reference not found for account ID: {}", accountId);
                    return new IllegalArgumentException("Card cross-reference not found for account ID: " + accountId);
                });

        Customer customer = customerRepository.findByCustomerId(cardXref.getCustomerId())
                .orElseThrow(() -> {
                    log.error("Customer not found with ID: {}", cardXref.getCustomerId());
                    return new IllegalArgumentException("Customer not found with ID: " + cardXref.getCustomerId());
                });

        log.info("Successfully retrieved account view for account ID: {}", accountId);
        return convertToAccountViewResponse(account, customer);
    }

    private String formatSSN(String ssn) {
        if (ssn == null || ssn.trim().isEmpty()) {
            return null;
        }

        String cleanedSSN = ssn.replaceAll("[^0-9]", "");

        if (cleanedSSN.length() != 9) {
            log.warn("SSN does not have 9 digits, returning as-is: {}", ssn);
            return ssn;
        }

        return String.format("%s-%s-%s",
                cleanedSSN.substring(0, 3),
                cleanedSSN.substring(3, 5),
                cleanedSSN.substring(5, 9));
    }

    private AccountViewResponseDto convertToAccountViewResponse(Account account, Customer customer) {
        AccountViewResponseDto response = new AccountViewResponseDto();
        
        response.setAccountId(account.getAccountId());
        response.setActiveStatus(account.getActiveStatus());
        response.setCurrentBalance(account.getCurrentBalance());
        response.setCreditLimit(account.getCreditLimit());
        response.setCashCreditLimit(account.getCashCreditLimit());
        response.setOpenDate(account.getOpenDate());
        response.setExpirationDate(account.getExpirationDate());
        response.setReissueDate(account.getReissueDate());
        response.setCurrentCycleCredit(account.getCurrentCycleCredit());
        response.setCurrentCycleDebit(account.getCurrentCycleDebit());
        response.setGroupId(account.getGroupId());
        
        response.setCustomerId(customer.getCustomerId());
        response.setFirstName(customer.getFirstName());
        response.setMiddleName(customer.getMiddleName());
        response.setLastName(customer.getLastName());
        response.setSsn(formatSSN(customer.getSsn()));
        response.setFicoScore(customer.getFicoScore());
        response.setDateOfBirth(customer.getDateOfBirth());
        response.setAddressLine1(customer.getAddressLine1());
        response.setAddressLine2(customer.getAddressLine2());
        response.setCity(customer.getCity());
        response.setStateCode(customer.getStateCode());
        response.setZipCode(customer.getZipCode());
        response.setCountryCode(customer.getCountryCode());
        response.setPhoneNumber1(customer.getPhoneNumber1());
        response.setPhoneNumber2(customer.getPhoneNumber2());
        response.setGovernmentIssuedId(customer.getGovernmentIssuedId());
        response.setEftAccountId(customer.getEftAccountId());
        response.setPrimaryCardHolderIndicator(customer.getPrimaryCardHolderIndicator());
        
        return response;
    }
}
