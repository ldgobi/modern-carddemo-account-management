# Account and Card Data Management API - OpenAPI Summary

## Overview
This API provides comprehensive functionality for managing credit card accounts, customers, and related financial operations including account viewing, updating, and interest calculation.

## Base Information
- **Base URL**: `/api`
- **Version**: 1.0.0
- **Content Type**: `application/json`

## API Endpoints

### Account View Management

#### Get Account View
Retrieves comprehensive account details combined with customer information.

- **Endpoint**: `GET /api/accounts/{accountId}/view`
- **Description**: Retrieve account view with customer details by account ID
- **Path Parameters**:
  - `accountId` (Long, required): The unique identifier of the account (11-digit number)
- **Response**: `200 OK`
  - Returns `AccountViewResponseDto` containing:
    - Account information (ID, status, balances, limits, dates)
    - Customer information (name, SSN formatted as XXX-XX-XXXX, contact details, address)
    - Financial details (credit limits, current balance, cycle amounts)
- **Error Responses**:
  - `400 Bad Request`: Invalid account ID format
  - `404 Not Found`: Account not found in the system
  - `500 Internal Server Error`: Server processing error

**Business Rules**:
- Account ID must be a non-zero 11-digit number
- Validates account exists in ACCTDAT
- Retrieves card cross-reference from CARDXREF using account ID
- Retrieves customer from CUSTDAT using customer ID
- Formats SSN as XXX-XX-XXXX for display
- Returns combined account and customer information

---

### Account Update Management

#### Update Account and Customer Data
Updates both account and customer information in a single transaction.

- **Endpoint**: `PUT /api/accounts/{accountId}/update`
- **Description**: Update account and customer information by account ID
- **Path Parameters**:
  - `accountId` (Long, required): The unique identifier of the account
- **Request Body**: `UpdateAccountUpdateRequestDto`
  - **Account Fields**:
    - `activeStatus` (String, optional): 'Y' or 'N'
    - `currentBalance` (BigDecimal, optional): Current balance (non-negative)
    - `creditLimit` (BigDecimal, optional): Credit limit (non-negative)
    - `cashCreditLimit` (BigDecimal, optional): Cash credit limit (non-negative)
    - `openDate` (LocalDate, optional): Account open date
    - `expirationDate` (LocalDate, optional): Account expiration date
    - `reissueDate` (LocalDate, optional): Card reissue date
    - `currentCycleCredit` (BigDecimal, optional): Current cycle credit
    - `currentCycleDebit` (BigDecimal, optional): Current cycle debit
    - `groupId` (String, optional): Account group ID (max 10 chars)
  - **Customer Fields**:
    - `firstName` (String, optional): First name (alphabetic only, max 25 chars)
    - `middleName` (String, optional): Middle name (alphabetic only, max 25 chars)
    - `lastName` (String, optional): Last name (alphabetic only, max 25 chars)
    - `ssn` (String, optional): Social Security Number (9 digits)
    - `dateOfBirth` (LocalDate, optional): Date of birth (must be in past)
    - `ficoScore` (Integer, optional): FICO score (300-850)
    - `addressLine1` (String, optional): Address line 1 (max 50 chars)
    - `addressLine2` (String, optional): Address line 2 (max 50 chars)
    - `city` (String, optional): City (max 50 chars)
    - `stateCode` (String, optional): State code (2 uppercase letters)
    - `zipCode` (String, optional): ZIP code (5 digits)
    - `countryCode` (String, optional): Country code (2-3 uppercase letters)
    - `phoneNumber1` (String, optional): Primary phone (format: (XXX)XXX-XXXX)
    - `phoneNumber2` (String, optional): Secondary phone (format: (XXX)XXX-XXXX)
    - `governmentIssuedId` (String, optional): Government ID (max 20 chars)
    - `eftAccountId` (String, optional): EFT account ID (max 10 chars)
    - `primaryCardHolderIndicator` (String, optional): 'Y' or 'N'
- **Response**: `200 OK`
  - Returns success message string
- **Error Responses**:
  - `400 Bad Request`: Invalid request data or validation failure
  - `404 Not Found`: Account or customer not found
  - `500 Internal Server Error`: Update operation failed

**Business Rules**:
- Validates all inputs comprehensively before update
- Account status must be 'Y' or 'N'
- All monetary amounts must be non-negative
- Dates must be in valid format
- SSN must be valid 9-digit number
- FICO score must be between 300 and 850
- Names must contain only alphabetic characters
- Phone numbers must be in valid format (XXX)XXX-XXXX
- State codes must be valid 2-letter codes
- ZIP codes must be 5-digit numbers
- Locks both account and customer records for update
- Checks if records were modified by another user
- Updates both records in a single transaction
- Rolls back if any error occurs

---

## Data Models

### AccountViewResponseDto
Complete account view with customer information.

**Fields**:
- `accountId` (Long): Account identifier
- `activeStatus` (String): Active status ('Y' or 'N')
- `currentBalance` (BigDecimal): Current account balance
- `creditLimit` (BigDecimal): Credit limit
- `cashCreditLimit` (BigDecimal): Cash credit limit
- `openDate` (LocalDate): Account open date
- `expirationDate` (LocalDate): Account expiration date
- `reissueDate` (LocalDate): Card reissue date
- `currentCycleCredit` (BigDecimal): Current cycle credit amount
- `currentCycleDebit` (BigDecimal): Current cycle debit amount
- `groupId` (String): Account group identifier
- `customerId` (Long): Customer identifier
- `firstName` (String): Customer first name
- `middleName` (String): Customer middle name
- `lastName` (String): Customer last name
- `ssn` (String): Social Security Number (formatted as XXX-XX-XXXX)
- `ficoScore` (Integer): FICO credit score
- `dateOfBirth` (LocalDate): Customer date of birth
- `addressLine1` (String): Address line 1
- `addressLine2` (String): Address line 2
- `city` (String): City
- `stateCode` (String): State code
- `zipCode` (String): ZIP code
- `countryCode` (String): Country code
- `phoneNumber1` (String): Primary phone number
- `phoneNumber2` (String): Secondary phone number
- `governmentIssuedId` (String): Government issued ID
- `eftAccountId` (String): EFT account ID
- `primaryCardHolderIndicator` (String): Primary card holder indicator

### UpdateAccountUpdateRequestDto
Request model for updating account and customer information.

**Validation Rules**:
- All fields are optional (partial updates supported)
- `activeStatus`: Must be 'Y' or 'N'
- `currentBalance`, `creditLimit`, `cashCreditLimit`: Must be >= 0
- `currentCycleCredit`, `currentCycleDebit`: Must be >= 0
- `openDate`: Must be in past or present
- `expirationDate`: Must be in future
- `firstName`, `middleName`, `lastName`: Alphabetic characters only
- `ssn`: Must be 9-digit number
- `dateOfBirth`: Must be in past
- `ficoScore`: Must be between 300 and 850
- `stateCode`: Must be 2 uppercase letters
- `zipCode`: Must be 5 digits
- `phoneNumber1`, `phoneNumber2`: Format (XXX)XXX-XXXX
- `primaryCardHolderIndicator`: Must be 'Y' or 'N'

---

## Error Handling

All endpoints follow standard HTTP status codes:

- **200 OK**: Successful operation
- **400 Bad Request**: Invalid input or validation failure
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server-side error

Error responses include descriptive messages to help identify the issue.

---

## Security Considerations

- SSN is formatted for display (XXX-XX-XXXX) to protect sensitive information
- All monetary transactions are validated for non-negative values
- Account and customer records are locked during updates to prevent concurrent modification
- Comprehensive input validation prevents invalid data entry

---

## Database Schema

### Tables
1. **accounts**: Stores account information
2. **customers**: Stores customer information
3. **cards**: Stores card information
4. **card_xref**: Cross-reference between cards, accounts, and customers
5. **transactions**: Stores transaction records
6. **disclosure_groups**: Stores interest rate information
7. **transaction_category_balances**: Stores transaction category balances

### Key Relationships
- Account → Customer (many-to-one)
- Card → Account (many-to-one)
- Card → Customer (many-to-one)
- CardXref → Account (many-to-one)
- CardXref → Customer (many-to-one)
- TransactionCategoryBalance → Account (many-to-one)

---

## Implementation Notes

### Technology Stack
- **Framework**: Spring Boot 3.x
- **Database**: MySQL/PostgreSQL (via JPA/Hibernate)
- **API Documentation**: OpenAPI 3.0 (Swagger)
- **Validation**: Jakarta Bean Validation
- **Logging**: SLF4J with Logback

### Key Features
1. **Account View**: Combines account and customer data in a single response
2. **Account Update**: Updates both account and customer in a single transaction
3. **Comprehensive Validation**: All inputs validated according to business rules
4. **Transaction Management**: Ensures data consistency with proper transaction handling
5. **Error Handling**: Detailed error messages for troubleshooting
6. **Audit Trail**: Automatic timestamps for created_at and updated_at fields

### Business Logic Implementation
- Account number validation (11-digit non-zero number)
- SSN formatting for display security
- FICO score range validation (300-850)
- Phone number format validation
- State code and ZIP code validation
- Concurrent update detection and prevention
- Transactional updates for data consistency

---

## Future Enhancements

Potential areas for expansion:
1. Interest calculation batch processing
2. Transaction history retrieval
3. Card management operations
4. Customer search and filtering
5. Account statement generation
6. Payment processing
7. Credit limit adjustment workflows
8. Account closure procedures

---

## Contact & Support

For API support or questions, please contact the development team.

**Version**: 1.0.0  
**Last Updated**: 2024
