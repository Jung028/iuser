# iuser - User Center Service

A robust, enterprise-grade User Center service built on **SOFABoot** (Spring Boot extension) and **Java 17**. This project provides comprehensive user management and financial/card integration capabilities, following a high-concurrency, scalable multi-module architecture.

## 🚀 Overview

`iuser` is the central identity and financial integration hub for the fintech ecosystem. It manages the full user lifecycle, from secure authentication and registration to complex financial instrument management like credit card integration via Stripe.

### Key Capabilities
- **Identity Management**: Multi-factor authentication (OTP), password security, and JWT-based session management.
- **Financial Integration**: Secure card storage and retrieval, automatic reload configurations, and seamless Stripe provider integration.
- **Observability**: Built-in distributed tracing (`traceId`) across all logs for easier debugging in microservice environments.
- **High Performance**: Tiered caching using Redis to minimize database load on frequent user profile lookups.

---

## 🛠 Tech Stack

- **Framework**: [SOFABoot 4.6.0](https://www.sofastack.tech/en/projects/sofa-boot/overview/) (Spring Boot 3.3.2)
- **Language**: Java 17
- **Persistence**: MyBatis 4.0.1 with MySQL/PostgreSQL support
- **Cache**: Redis (Spring Data Redis)
- **Security**: JWT, Spring Security, Stripe SDK
- **Observability**: Logback with Distributed Tracing (TraceID)
- **Build Tool**: Maven

---

## 📂 Project Structure

The project follows a standard SOFABoot modular architecture:

| Module | Purpose |
| :--- | :--- |
| `app/bootstrap` | Entry point (`Main.java`) and global runtime configuration. |
| `app/web` | REST Controllers, CORS, Security, and TraceID filters. |
| `app/biz/service/impl` | Core business logic and service implementations. |
| `app/biz/shared` | Reusable business components and shared beans. |
| `app/core/service` | Internal domain services. |
| `app/core/model` | Domain entities, enums, and business exceptions. |
| `app/common/service/facade` | Public service interfaces and DTOs (The "API" layer). |
| `app/common/service/integration` | Clients for external services (Stripe, SMS, etc.). |
| `app/common/dal` | Data Access Layer (MyBatis Mappers, SQL Maps). |
| `app/common/util` | General-purpose utility classes. |

---

## ⚙️ Getting Started

### Prerequisites
- **JDK 17**
- **Maven 3.8+**
- **MySQL 8.0+** or **PostgreSQL**
- **Redis 6.0+**

### Configuration
1.  **Database & Redis**: Configure your connection strings in `app/bootstrap/src/main/resources/config/application.properties`.
2.  **Logging**: Logs are automatically written to the `logs/` directory.

### Build and Run
```bash
# Install dependencies and build
mvn clean install

# Launch the application
cd app/bootstrap
mvn spring-boot:run
```

---

## 🛠 Development & Operations

### Generating DAL Code
The project uses MyBatis Generator. To update Data Objects and Mappers based on the database schema:
```bash
mvn -Dmybatis.generator.overwrite=true org.mybatis.generator:mybatis-generator-maven-plugin:1.4.1:generate
```

### Testing
Unit and integration tests are located in `src/test` directories across modules.
```bash
mvn test
```

### Logging & Tracing
All logs include a `traceId` for request tracking. You can find logs at:
- `logs/usercenter-web.log` (Current)
- `logs/usercenter-web.YYYY-MM-DD.log` (Archived)

Example Log Format:
`2026-04-26 10:00:00 INFO  traceId=abc123xyz c.a.u.b.u.i.UserServiceImpl - User login successful: 10001`

---

## 📡 API Reference

### User Management (`/user/basic`)
| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/login.json` | `POST` | Authenticate user and return JWT. |
| `/sendOTP.json` | `POST` | Trigger SMS/Email OTP code. |
| `/register.json` | `POST` | Register a new user account. |
| `/queryUserInfo.json` | `POST` | Get detailed user profile. |

### Top-Up & Cards (`/user/topup`)
| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/queryCardDetails.json` | `POST` | Get stored card info (masked). |
| `/createNewCard.json` | `POST` | Register a new payment instrument. |
| `/toggleAutoReloadConfig.json` | `POST` | Enable/Disable automatic wallet top-ups. |

---

## 📄 License
Copyright © 2026 Alipay/Adam. Licensed under the [LICENSE](LICENSE) file.
