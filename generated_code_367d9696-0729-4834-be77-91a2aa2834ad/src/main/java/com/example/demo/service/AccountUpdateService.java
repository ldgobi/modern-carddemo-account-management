package com.example.demo.service;

import com.example.demo.dto.UpdateAccountUpdateRequestDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountUpdateService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    private static final Pattern SSN_PATTERN = Pattern.compile("^\\d{9}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\(?\\d{3}\\)?\\d{3}-\\d{4}$");
    private static final Pattern ALPHABETIC_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern STATE_CODE_PATTERN = Pattern.compile("^[A-Z]{2}$");
    private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("^\\d{5}$");
    private static final int MIN_FICO_SCORE = 300;
    private static final int MAX_FICO_SCORE = 850;

    @Transactional
    public String updateAccount(Long accountId, UpdateAccountUpdateRequestDto request) {
        log.info("Starting account update for accountId: {}", accountId);

        try {
            String validationError = validateInputs(request);
            if (validationError != null) {
                log.error("Validation failed: {}", validationError);
                return validationError;
            }

            Account account = accountRepository.findByAccountId(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));

            Customer customer = customerRepository.findByCustomerId(account.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + account.getCustomerId()));

            updateAccountFields(account, request);
            updateCustomerFields(customer, request);

            accountRepository.save(account);
            customerRepository.save(customer);

            log.info("Successfully updated account and customer for accountId: {}", accountId);
            return "Success: Account and customer information updated successfully";

        } catch (Exception e) {
            log.error("Error updating account: {}", e.getMessage(), e);
            throw new RuntimeException("Error: Failed to update account - " + e.getMessage());
        }
    }

    private void updateAccountFields(Account account, UpdateAccountUpdateRequestDto request) {
        if (request.getActiveStatus() != null) {
            account.setActiveStatus(request.getActiveStatus());
        }
        if (request.getCreditLimit() != null) {
            account.setCreditLimit(request.getCreditLimit());
        }
        if (request.getCurrentBalance() != null) {
            account.setCurrentBalance(request.getCurrentBalance());
        }
        if (request.getCashCreditLimit() != null) {
            account.setCashCreditLimit(request.getCashCreditLimit());
        }
        if (request.getOpenDate() != null) {
            account.setOpenDate(request.getOpenDate());
        }
        if (request.getExpirationDate() != null) {
            account.setExpirationDate(request.getExpirationDate());
        }
        if (request.getReissueDate() != null) {
            account.setReissueDate(request.getReissueDate());
        }
        if (request.getCurrentCycleCredit() != null) {
            account.setCurrentCycleCredit(request.getCurrentCycleCredit());
        }
        if (request.getCurrentCycleDebit() != null) {
            account.setCurrentCycleDebit(request.getCurrentCycleDebit());
        }
        if (request.getGroupId() != null) {
            account.setGroupId(request.getGroupId());
        }
    }

    private void updateCustomerFields(Customer customer, UpdateAccountUpdateRequestDto request) {
        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getMiddleName() != null) {
            customer.setMiddleName(request.getMiddleName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }
        if (request.getSsn() != null) {
            customer.setSsn(request.getSsn());
        }
        if (request.getDateOfBirth() != null) {
            customer.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getFicoScore() != null) {
            customer.setFicoScore(request.getFicoScore());
        }
        if (request.getAddressLine1() != null) {
            customer.setAddressLine1(request.getAddressLine1());
        }
        if (request.getAddressLine2() != null) {
            customer.setAddressLine2(request.getAddressLine2());
        }
        if (request.getCity() != null) {
            customer.setCity(request.getCity());
        }
        if (request.getStateCode() != null) {
            customer.setStateCode(request.getStateCode());
        }
        if (request.getZipCode() != null) {
            customer.setZipCode(request.getZipCode());
        }
        if (request.getCountryCode() != null) {
            customer.setCountryCode(request.getCountryCode());
        }
        if (request.getPhoneNumber1() != null) {
            customer.setPhoneNumber1(request.getPhoneNumber1());
        }
        if (request.getPhoneNumber2() != null) {
            customer.setPhoneNumber2(request.getPhoneNumber2());
        }
        if (request.getGovernmentIssuedId() != null) {
            customer.setGovernmentIssuedId(request.getGovernmentIssuedId());
        }
        if (request.getEftAccountId() != null) {
            customer.setEftAccountId(request.getEftAccountId());
        }
        if (request.getPrimaryCardHolderIndicator() != null) {
            customer.setPrimaryCardHolderIndicator(request.getPrimaryCardHolderIndicator());
        }
    }

    private String validateInputs(UpdateAccountUpdateRequestDto request) {
        if (request.getActiveStatus() != null) {
            String status = request.getActiveStatus().toUpperCase();
            if (!status.equals("Y") && !status.equals("N")) {
                return "Error: Account status must be 'Y' or 'N'";
            }
        }

        if (request.getCreditLimit() != null && request.getCreditLimit().compareTo(BigDecimal.ZERO) < 0) {
            return "Error: Credit limit cannot be negative";
        }
        if (request.getCurrentBalance() != null && request.getCurrentBalance().compareTo(BigDecimal.ZERO) < 0) {
            return "Error: Current balance cannot be negative";
        }
        if (request.getCashCreditLimit() != null && request.getCashCreditLimit().compareTo(BigDecimal.ZERO) < 0) {
            return "Error: Cash credit limit cannot be negative";
        }
        if (request.getCurrentCycleCredit() != null && request.getCurrentCycleCredit().compareTo(BigDecimal.ZERO) < 0) {
            return "Error: Current cycle credit cannot be negative";
        }
        if (request.getCurrentCycleDebit() != null && request.getCurrentCycleDebit().compareTo(BigDecimal.ZERO) < 0) {
            return "Error: Current cycle debit cannot be negative";
        }

        if (request.getSsn() != null && !SSN_PATTERN.matcher(request.getSsn()).matches()) {
            return "Error: SSN must be a valid 9-digit number";
        }

        if (request.getFicoScore() != null) {
            if (request.getFicoScore() < MIN_FICO_SCORE || request.getFicoScore() > MAX_FICO_SCORE) {
                return "Error: FICO score must be between 300 and 850";
            }
        }

        if (request.getFirstName() != null && !ALPHABETIC_PATTERN.matcher(request.getFirstName()).matches()) {
            return "Error: First name must contain only alphabetic characters";
        }
        if (request.getMiddleName() != null && !request.getMiddleName().isEmpty() && 
            !ALPHABETIC_PATTERN.matcher(request.getMiddleName()).matches()) {
            return "Error: Middle name must contain only alphabetic characters";
        }
        if (request.getLastName() != null && !ALPHABETIC_PATTERN.matcher(request.getLastName()).matches()) {
            return "Error: Last name must contain only alphabetic characters";
        }
        if (request.getCity() != null && !ALPHABETIC_PATTERN.matcher(request.getCity()).matches()) {
            return "Error: City must contain only alphabetic characters";
        }

        if (request.getPhoneNumber1() != null && !PHONE_PATTERN.matcher(request.getPhoneNumber1()).matches()) {
            return "Error: Phone number 1 must be in format (XXX)XXX-XXXX";
        }
        if (request.getPhoneNumber2() != null && !request.getPhoneNumber2().isEmpty() && 
            !PHONE_PATTERN.matcher(request.getPhoneNumber2()).matches()) {
            return "Error: Phone number 2 must be in format (XXX)XXX-XXXX";
        }

        if (request.getStateCode() != null && !STATE_CODE_PATTERN.matcher(request.getStateCode()).matches()) {
            return "Error: State code must be a valid 2-letter code";
        }

        if (request.getZipCode() != null && !ZIP_CODE_PATTERN.matcher(request.getZipCode()).matches()) {
            return "Error: Zip code must be a valid 5-digit number";
        }

        return null;
    }
}
