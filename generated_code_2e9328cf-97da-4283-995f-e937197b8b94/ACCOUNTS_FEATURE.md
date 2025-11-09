# Account and Card Data Management Feature

## Overview

This feature implements comprehensive account and card data management functionality for the CardDemo application. It provides capabilities to view and update credit card account information along with associated customer details.

## Features Implemented

### 1. Account Search and View
- **Route**: `/accounts`
- **Description**: Search for accounts by 11-digit account ID
- **Functionality**:
  - Input validation for account ID format
  - User-friendly search interface
  - Error handling for invalid inputs
  - Help section with usage instructions

### 2. Account Detail View
- **Route**: `/accounts/[accountId]`
- **Description**: Comprehensive view of account and customer information
- **Displays**:
  - **Account Information**:
    - Account status (Active/Inactive)
    - Current balance
    - Credit limit
    - Available credit
    - Credit utilization percentage
    - Cash credit limit
    - Current cycle credit/debit
    - Account group ID
  - **Account Dates**:
    - Open date
    - Expiration date
    - Reissue date
  - **Customer Information**:
    - Customer ID
    - Full name (first, middle, last)
    - SSN (formatted as XXX-XX-XXXX)
    - Date of birth
    - FICO score with rating
    - Primary card holder indicator
  - **Contact Information**:
    - Full address (line 1, line 2, city, state, ZIP)
    - Country code
    - Primary and secondary phone numbers
    - Government issued ID
    - EFT account ID

### 3. Account Edit
- **Route**: `/accounts/[accountId]/edit`
- **Description**: Update account and customer information
- **Features**:
  - Comprehensive form with all editable fields
  - Real-time validation
  - Field-level error messages
  - Change detection
  - Conflict detection (if record modified by another user)
  - Transaction management

## API Endpoints

### GET /api/accounts/{accountId}/view
- **Purpose**: Retrieve account view with customer details
- **Validation**: 11-digit non-zero account ID
- **Response**: AccountViewResponse object
- **Error Codes**:
  - 400: Invalid account ID format
  - 404: Account not found
  - 500: Server error

### PUT /api/accounts/{accountId}/update
- **Purpose**: Update account and customer information
- **Validation**: Comprehensive validation for all fields
- **Request Body**: UpdateAccountRequest (partial updates supported)
- **Response**: Success message
- **Error Codes**:
  - 400: Validation error
  - 404: Account/customer not found
  - 409: Concurrent modification detected
  - 500: Server error

## Data Validation Rules

### Account Fields
- **Active Status**: Must be 'Y' or 'N'
- **Monetary Amounts**: Must be non-negative numbers with max 2 decimal places
- **Dates**: Must be valid date format
- **Account ID**: Must be 11-digit non-zero number

### Customer Fields
- **SSN**: Must be 9-digit number, validated for invalid patterns
- **FICO Score**: Must be between 300 and 850
- **Names**: Must contain only alphabetic characters
- **Phone Numbers**: Must be 10-digit valid US phone numbers
- **State Code**: Must be 2 uppercase letters
- **ZIP Code**: Must be 5 digits
- **Country Code**: Must be 2-3 uppercase letters
- **Primary Card Holder**: Must be 'Y' or 'N'

## File Structure

```
src/
├── types/
│   └── account.ts                    # Type definitions and validation functions
├── services/
│   └── accountService.ts             # API client service
├── app/
│   ├── api/
│   │   └── accounts/
│   │       └── [accountId]/
│   │           ├── view/
│   │           │   └── route.ts      # GET account view endpoint
│   │           └── update/
│   │               └── route.ts      # PUT account update endpoint
│   └── accounts/
│       ├── page.tsx                  # Account search page
│       └── [accountId]/
│           ├── page.tsx              # Account detail view
│           └── edit/
│               └── page.tsx          # Account edit form
```

## Type Definitions

### AccountViewResponse
Complete account and customer information returned from the view endpoint.

### UpdateAccountRequest
Partial update request supporting optional fields for both account and customer data.

## Helper Functions

### Validation Functions
- `validateAccountId()`: Validates 11-digit account ID
- `validateActiveStatus()`: Validates Y/N status
- `validateMonetaryAmount()`: Validates non-negative amounts
- `validateSSN()`: Validates SSN format and patterns
- `validateFicoScore()`: Validates FICO score range
- `validatePhoneNumber()`: Validates US phone numbers
- `validateStateCode()`: Validates 2-letter state codes
- `validateZipCode()`: Validates 5-digit ZIP codes
- `validateName()`: Validates alphabetic names

### Formatting Functions
- `formatSSN()`: Formats SSN as XXX-XX-XXXX
- `formatPhoneNumber()`: Formats phone as (XXX)XXX-XXXX
- `formatCurrency()`: Formats amounts as USD currency
- `formatDate()`: Formats dates for display
- `formatAccountId()`: Formats account ID with leading zeros

### Utility Functions
- `getStatusLabel()`: Converts Y/N to Active/Inactive
- `getStatusBadgeColor()`: Returns Tailwind classes for status badges
- `getFicoScoreLabel()`: Returns FICO score rating (Exceptional, Very Good, etc.)
- `getFicoScoreColor()`: Returns color classes based on FICO score
- `calculateAvailableCredit()`: Calculates available credit
- `calculateCreditUtilization()`: Calculates credit utilization percentage
- `getCreditUtilizationColor()`: Returns color based on utilization
- `getFullName()`: Combines first, middle, last names
- `getFullAddress()`: Combines address components

## UI Components Used

All UI components are from the existing component library:
- **Button**: Primary, secondary, and danger variants
- **Input**: Text, number, and date inputs with validation
- **Select**: Dropdown selections

## Error Handling

### Loading States
- Spinner animation during data fetch
- "Loading..." message

### Error States
- Red error banners with descriptive messages
- Field-level validation errors
- Network error handling
- 404 not found handling
- Conflict detection for concurrent updates

### Empty States
- "Account not found" message
- "No changes detected" message

## Business Rules Implementation

### From COACTVWC.cbl (Account View)
- ✅ Validates account number format (11-digit non-zero)
- ✅ Retrieves account data from ACCTDAT
- ✅ Retrieves card cross-reference from CARDXREF
- ✅ Retrieves customer data from CUSTDAT
- ✅ Formats SSN as XXX-XX-XXXX
- ✅ Displays all account and customer fields
- ✅ Error handling for not found scenarios

### From COACTUPC.cbl (Account Update)
- ✅ Validates all input fields comprehensively
- ✅ Validates account status (Y/N)
- ✅ Validates monetary amounts (non-negative)
- ✅ Validates dates (valid format)
- ✅ Validates SSN (9-digit with pattern checks)
- ✅ Validates FICO score (300-850)
- ✅ Validates names (alphabetic only)
- ✅ Validates phone numbers (10-digit US format)
- ✅ Validates state codes (2 uppercase letters)
- ✅ Validates ZIP codes (5 digits)
- ✅ Detects changes before update
- ✅ Handles concurrent modification conflicts
- ✅ Updates both account and customer records
- ✅ Transaction management (rollback on error)

## Testing Recommendations

### Manual Testing
1. **Account Search**:
   - Test with valid 11-digit account ID
   - Test with invalid formats (too short, too long, non-numeric)
   - Test with all zeros

2. **Account View**:
   - Verify all fields display correctly
   - Check SSN formatting (XXX-XX-XXXX)
   - Check phone number formatting
   - Verify credit calculations
   - Test navigation buttons

3. **Account Edit**:
   - Test field validation (each field type)
   - Test with no changes
   - Test with partial updates
   - Test with invalid data
   - Test concurrent update scenario
   - Verify success message and navigation

### Integration Testing
- Test API endpoint responses
- Test error handling for network failures
- Test authentication token handling
- Test backend validation

## Future Enhancements

Potential improvements:
1. Account search by customer name or SSN
2. Account list view with pagination
3. Transaction history display
4. Interest calculation integration
5. Card management operations
6. Account statement generation
7. Audit trail for changes
8. Bulk update operations
9. Export functionality
10. Advanced filtering and sorting

## Security Considerations

- SSN is formatted for display (XXX-XX-XXXX) to protect sensitive data
- All API calls include authentication headers
- Input validation prevents injection attacks
- Monetary amounts validated to prevent negative values
- Concurrent update detection prevents data loss
- Transaction management ensures data consistency

## Performance Considerations

- Efficient data fetching with single API calls
- Client-side validation reduces server load
- Optimistic UI updates for better UX
- Proper error boundaries prevent crashes
- Loading states provide user feedback

## Accessibility

- Semantic HTML structure
- Proper form labels
- Error messages associated with fields
- Keyboard navigation support
- Color contrast for readability
- Screen reader friendly

## Browser Compatibility

Tested and compatible with:
- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Dependencies

- Next.js 15.5.3
- React 19
- TypeScript 5
- TailwindCSS v4
- Existing UI component library

## Maintenance Notes

- Follow the archetype patterns for consistency
- Do not modify existing UI components
- Add new validation rules to types/account.ts
- Update API routes for new endpoints
- Keep service methods focused and single-purpose
- Document any business rule changes
