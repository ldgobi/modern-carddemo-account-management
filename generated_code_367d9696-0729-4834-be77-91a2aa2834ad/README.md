# Account and Card Data Management System

A modern Spring Boot application for managing credit card accounts, customers, and related financial operations. This system modernizes legacy COBOL programs into a RESTful API architecture.

## Features

- **Account View Management**: Retrieve comprehensive account details with customer information
- **Account Update Management**: Update account and customer data with comprehensive validation
- **Data Integrity**: Transaction management ensures consistency across updates
- **Comprehensive Validation**: All inputs validated according to business rules
- **RESTful API**: Clean, resource-oriented endpoints
- **OpenAPI Documentation**: Integrated Swagger UI for API exploration

## Technology Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA / Hibernate
- MySQL/PostgreSQL
- Flyway (Database Migrations)
- OpenAPI 3.0 / Swagger
- Maven
- Lombok
- SLF4J / Logback

## Prerequisites

- JDK 17 or higher
- Maven 3.6+
- MySQL 8.0+ or PostgreSQL 12+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd <project-directory>
```

### 2. Configure Database

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/cardmanagement
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Flyway Configuration
spring.flyway.enabled=true
```

### 3. Create Database

```sql
CREATE DATABASE cardmanagement;
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 6. Access API Documentation

Open your browser and navigate to:
```
http://localhost:8080/swagger-ui.html
```

## API Endpoints

### Account View Management

#### Get Account View
```http
GET /api/accounts/{accountId}/view
```

Retrieves comprehensive account details with customer information.

**Path Parameters:**
- `accountId` (Long): The account identifier (11-digit number)

**Response:** `200 OK`
```json
{
  "accountId": 12345678901,
  "activeStatus": "Y",
  "currentBalance": 5000.00,
  "creditLimit": 10000.00,
  "cashCreditLimit": 2000.00,
  "openDate": "2023-01-15",
  "expirationDate": "2026-01-15",
  "customerId": 123456789,
  "firstName": "John",
  "lastName": "Doe",
  "ssn": "123-45-6789",
  "ficoScore": 750,
  ...
}
```

### Account Update Management

#### Update Account and Customer
```http
PUT /api/accounts/{accountId}/update
```

Updates account and customer information.

**Path Parameters:**
- `accountId` (Long): The account identifier

**Request Body:**
```json
{
  "activeStatus": "Y",
  "creditLimit": 15000.00,
  "firstName": "John",
  "lastName": "Doe",
  "ficoScore": 780,
  "phoneNumber1": "(212)555-1234",
  "stateCode": "NY",
  "zipCode": "10001"
}
```

**Response:** `200 OK`
```json
"Success: Account and customer information updated successfully"
```

## Database Schema

### Main Tables

1. **customers** - Customer personal and contact information
2. **accounts** - Account balances, limits, and status
3. **cards** - Credit card information
4. **card_xref** - Cross-reference between cards, accounts, and customers
5. **transactions** - Transaction records
6. **disclosure_groups** - Interest rate information
7. **transaction_category_balances** - Transaction category balances

### Entity Relationships

```
Customer (1) ←→ (N) Account
Customer (1) ←→ (N) Card
Account (1) ←→ (N) Card
Account (1) ←→ (N) CardXref
Account (1) ←→ (N) TransactionCategoryBalance
```

## Business Rules

### Account View
- Account ID must be a non-zero 11-digit number
- Retrieves account, card cross-reference, and customer data
- Formats SSN as XXX-XX-XXXX for display
- Returns combined account and customer information

### Account Update
- **Account Status**: Must be 'Y' or 'N'
- **Monetary Amounts**: Must be non-negative
- **SSN**: Must be valid 9-digit number
- **FICO Score**: Must be between 300 and 850
- **Names**: Must contain only alphabetic characters
- **Phone Numbers**: Format (XXX)XXX-XXXX
- **State Codes**: Must be valid 2-letter codes
- **ZIP Codes**: Must be 5-digit numbers
- Updates both account and customer in single transaction
- Validates all inputs before processing

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── controller/      # REST Controllers
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── entity/          # JPA Entities
│   │       ├── repository/      # Spring Data Repositories
│   │       └── service/         # Business Logic Services
│   └── resources/
│       ├── db/migration/        # Flyway SQL Scripts
│       └── application.properties
└── test/                        # Unit and Integration Tests
```

## Development

### Running Tests

```bash
mvn test
```

### Building for Production

```bash
mvn clean package -DskipTests
```

The JAR file will be created in the `target/` directory.

### Running in Production

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Configuration

### Application Properties

Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/cardmanagement
spring.datasource.username=root
spring.datasource.password=password

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.com.example.demo=INFO
logging.level.org.springframework.web=INFO

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# OpenAPI
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## Error Handling

The API uses standard HTTP status codes:

- `200 OK` - Successful operation
- `400 Bad Request` - Invalid input or validation failure
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server-side error

Error responses include descriptive messages:

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "FICO score must be between 300 and 850",
  "path": "/api/accounts/12345678901/update"
}
```

## Security Considerations

- SSN is formatted for display (XXX-XX-XXXX) to protect sensitive information
- All monetary transactions are validated for non-negative values
- Comprehensive input validation prevents invalid data entry
- Transaction management ensures data consistency

## Monitoring and Health

Health check endpoint:
```http
GET /actuator/health
```

## Documentation

- **OpenAPI Specification**: See `openapi-summary.md`
- **Implementation Details**: See `IMPLEMENTATION_SUMMARY.md`
- **API Documentation**: Available at `/swagger-ui.html` when running

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support or questions, please contact the development team.

## Acknowledgments

- Based on legacy COBOL programs: COACTVWC.cbl, COACTUPC.cbl
- Modernized using Spring Boot best practices
- Follows SOLID principles and clean code guidelines
