package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountUpdateRequestDto {
    
    @Schema(description = "Active status of the account", example = "Y")
    @Pattern(regexp = "^[YN]$", message = "Active status must be 'Y' or 'N'")
    private String activeStatus;
    
    @Schema(description = "Current balance of the account", example = "1500.50")
    @DecimalMin(value = "0.0", message = "Current balance must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Current balance must have at most 10 integer digits and 2 decimal places")
    private BigDecimal currentBalance;
    
    @Schema(description = "Credit limit of the account", example = "5000.00")
    @DecimalMin(value = "0.0", message = "Credit limit must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Credit limit must have at most 10 integer digits and 2 decimal places")
    private BigDecimal creditLimit;
    
    @Schema(description = "Cash credit limit of the account", example = "1000.00")
    @DecimalMin(value = "0.0", message = "Cash credit limit must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Cash credit limit must have at most 10 integer digits and 2 decimal places")
    private BigDecimal cashCreditLimit;
    
    @Schema(description = "Account open date", example = "2023-01-15")
    @PastOrPresent(message = "Open date must be in the past or present")
    private LocalDate openDate;
    
    @Schema(description = "Account expiration date", example = "2026-01-15")
    @Future(message = "Expiration date must be in the future")
    private LocalDate expirationDate;
    
    @Schema(description = "Account reissue date", example = "2025-12-01")
    private LocalDate reissueDate;
    
    @Schema(description = "Current cycle credit amount", example = "250.75")
    @DecimalMin(value = "0.0", message = "Current cycle credit must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Current cycle credit must have at most 10 integer digits and 2 decimal places")
    private BigDecimal currentCycleCredit;
    
    @Schema(description = "Current cycle debit amount", example = "180.25")
    @DecimalMin(value = "0.0", message = "Current cycle debit must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Current cycle debit must have at most 10 integer digits and 2 decimal places")
    private BigDecimal currentCycleDebit;
    
    @Schema(description = "Group ID associated with the account", example = "GRP001")
    @Size(max = 10, message = "Group ID must not exceed 10 characters")
    private String groupId;
    
    @Schema(description = "First name of the customer", example = "John")
    @Size(max = 25, message = "First name must not exceed 25 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "First name must contain only letters and spaces")
    private String firstName;
    
    @Schema(description = "Middle name of the customer", example = "Michael")
    @Size(max = 25, message = "Middle name must not exceed 25 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Middle name must contain only letters and spaces")
    private String middleName;
    
    @Schema(description = "Last name of the customer", example = "Doe")
    @Size(max = 25, message = "Last name must not exceed 25 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Last name must contain only letters and spaces")
    private String lastName;
    
    @Schema(description = "Social Security Number", example = "123456789")
    @Pattern(regexp = "^\\d{9}$", message = "SSN must be a 9-digit number")
    private String ssn;
    
    @Schema(description = "Date of birth of the customer", example = "1985-06-15")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    
    @Schema(description = "FICO credit score", example = "720")
    @Min(value = 300, message = "FICO score must be at least 300")
    @Max(value = 850, message = "FICO score must not exceed 850")
    private Integer ficoScore;
    
    @Schema(description = "Address line 1", example = "123 Main Street")
    @Size(max = 50, message = "Address line 1 must not exceed 50 characters")
    private String addressLine1;
    
    @Schema(description = "Address line 2", example = "Apt 4B")
    @Size(max = 50, message = "Address line 2 must not exceed 50 characters")
    private String addressLine2;
    
    @Schema(description = "City", example = "New York")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;
    
    @Schema(description = "State code", example = "NY")
    @Size(min = 2, max = 2, message = "State code must be exactly 2 characters")
    @Pattern(regexp = "^[A-Z]{2}$", message = "State code must be 2 uppercase letters")
    private String stateCode;
    
    @Schema(description = "ZIP code", example = "10001")
    @Pattern(regexp = "^\\d{5}$", message = "ZIP code must be a 5-digit number")
    private String zipCode;
    
    @Schema(description = "Country code", example = "USA")
    @Size(min = 2, max = 3, message = "Country code must be 2 or 3 characters")
    @Pattern(regexp = "^[A-Z]{2,3}$", message = "Country code must be uppercase letters")
    private String countryCode;
    
    @Schema(description = "Primary phone number", example = "(212)555-1234")
    @Pattern(regexp = "^\\(?\\d{3}\\)?\\d{3}-\\d{4}$", message = "Phone number must be in format (XXX)XXX-XXXX")
    private String phoneNumber1;
    
    @Schema(description = "Secondary phone number", example = "(212)555-5678")
    @Pattern(regexp = "^\\(?\\d{3}\\)?\\d{3}-\\d{4}$", message = "Phone number must be in format (XXX)XXX-XXXX")
    private String phoneNumber2;
    
    @Schema(description = "Government issued ID", example = "DL123456789")
    @Size(max = 20, message = "Government issued ID must not exceed 20 characters")
    private String governmentIssuedId;
    
    @Schema(description = "EFT account ID", example = "EFT987654")
    @Size(max = 10, message = "EFT account ID must not exceed 10 characters")
    private String eftAccountId;
    
    @Schema(description = "Primary card holder indicator", example = "Y")
    @Pattern(regexp = "^[YN]$", message = "Primary card holder indicator must be 'Y' or 'N'")
    private String primaryCardHolderIndicator;
}
