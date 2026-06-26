# 🚀 Campus Lost & Found Portal - Backend

This is the backend service for the Campus Lost & Found Portal. It provides REST APIs for user authentication, lost and found item management, claim requests, and admin operations.

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT Authentication
- MySQL
- Maven
- Railway (Deployment)

## ✨ Features

- User Registration & Login
- JWT-based Authentication
- Lost Item Management
- Found Item Management
- Claim Requests
- Admin Approval System
- Audit Logs
- RESTful APIs

## 📂 Project Structure

```
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── entity/
 ├── dto/
 ├── config/
 └── exception/
```

## ⚙️ Getting Started

### Clone the repository

```bash
git clone https://github.com/sxnchita/lost-found-portal-backend.git
```

### Install dependencies

```bash
mvn clean install
```

### Run the application

```bash
mvn spring-boot:run
```

## 🗄️ Database

- MySQL
- Spring Data JPA
- Hibernate

Configure your database connection in:

```
src/main/resources/application.properties
```

## 🌐 Deployment

- Backend: Railway
- Frontend: Vercel

## 📌 API Endpoints

- `/auth`
- `/users`
- `/lost-items`
- `/found-items`
- `/claim-requests`

## 👩‍💻 Author

**Sanchita Senapati**

GitHub: https://github.com/sxnchita

---

⭐ If you find this project useful, consider giving it a star!
