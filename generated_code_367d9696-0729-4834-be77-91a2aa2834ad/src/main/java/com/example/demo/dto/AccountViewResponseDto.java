package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountViewResponseDto {
    
    @Schema(description = "Account ID", example = "12345678901")
    private Long accountId;
    
    @Schema(description = "Active status of the account", example = "Y")
    private String activeStatus;
    
    @Schema(description = "Current balance of the account", example = "5000.00")
    private BigDecimal currentBalance;
    
    @Schema(description = "Credit limit of the account", example = "10000.00")
    private BigDecimal creditLimit;
    
    @Schema(description = "Cash credit limit of the account", example = "2000.00")
    private BigDecimal cashCreditLimit;
    
    @Schema(description = "Account open date", example = "2023-01-15")
    private LocalDate openDate;
    
    @Schema(description = "Account expiration date", example = "2026-01-15")
    private LocalDate expirationDate;
    
    @Schema(description = "Account reissue date", example = "2025-12-01")
    private LocalDate reissueDate;
    
    @Schema(description = "Current cycle credit amount", example = "1500.00")
    private BigDecimal currentCycleCredit;
    
    @Schema(description = "Current cycle debit amount", example = "800.00")
    private BigDecimal currentCycleDebit;
    
    @Schema(description = "Group ID", example = "GRP001")
    private String groupId;
    
    @Schema(description = "Customer ID", example = "123456789")
    private Long customerId;
    
    @Schema(description = "First name of the customer", example = "John")
    private String firstName;
    
    @Schema(description = "Middle name of the customer", example = "Michael")
    private String middleName;
    
    @Schema(description = "Last name of the customer", example = "Doe")
    private String lastName;
    
    @Schema(description = "Social Security Number formatted as XXX-XX-XXXX", example = "123-45-6789")
    private String ssn;
    
    @Schema(description = "FICO credit score", example = "750")
    private Integer ficoScore;
    
    @Schema(description = "Date of birth", example = "1985-06-15")
    private LocalDate dateOfBirth;
    
    @Schema(description = "Address line 1", example = "123 Main Street")
    private String addressLine1;
    
    @Schema(description = "Address line 2", example = "Apt 4B")
    private String addressLine2;
    
    @Schema(description = "City", example = "New York")
    private String city;
    
    @Schema(description = "State code", example = "NY")
    private String stateCode;
    
    @Schema(description = "ZIP code", example = "10001")
    private String zipCode;
    
    @Schema(description = "Country code", example = "USA")
    private String countryCode;
    
    @Schema(description = "Primary phone number", example = "(212)555-1234")
    private String phoneNumber1;
    
    @Schema(description = "Secondary phone number", example = "(212)555-5678")
    private String phoneNumber2;
    
    @Schema(description = "Government issued ID", example = "DL123456789")
    private String governmentIssuedId;
    
    @Schema(description = "EFT account ID", example = "EFT987654")
    private String eftAccountId;
    
    @Schema(description = "Primary card holder indicator", example = "Y")
    private String primaryCardHolderIndicator;
}
