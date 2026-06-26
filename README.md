# FitnessTracker-Project
A Spring Boot REST API for tracking fitness activities recommendations, built with a layered architecture (Controller-Service-Repository-DTO). Features JWT-based authentication and authorization, Spring Data JPA for persistence, and Swagger documentation. Deployed live on Render.


## 🌐 Live Demo

**Swagger UI:** https://fitness-project-latest-chws.onrender.com/swagger-ui/index.html

## 📸 Screenshots

<img width="1905" height="882" alt="Screenshot 2026-06-26 145046" src="https://github.com/user-attachments/assets/e46a50ab-2ae8-4b03-98b8-41507c2055a9" />
<img width="1920" height="901" alt="Screenshot 2026-06-26 145102" src="https://github.com/user-attachments/assets/13d123dc-a104-4001-8b4a-92fff204d147" />
<img width="1612" height="899" alt="Screenshot 2026-06-26 145220" src="https://github.com/user-attachments/assets/3057dd76-bab4-4899-8937-554458f50a66" />
<img width="1310" height="642" alt="Screenshot 2026-06-26 150104" src="https://github.com/user-attachments/assets/e3002f9a-6e2b-4485-97ec-c566dcb4452a" />




## Features

- **User Authentication & Authorization** — Secure registration and login using Spring Security and JWT tokens
- **Activity Tracking** — Log fitness activities (Running, Cycling, Yoga, HIIT, Swimming, and more) with duration, calories burned, and custom metrics
- **AI-Powered Recommendations** — Generates personalized improvement, suggestion, and safety insights based on logged activities
- **API Documentation** — Interactive API docs via Swagger/OpenAPI
- **Role-Based Access** — Supports USER and ADMIN roles

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Security:** Spring Security, JWT
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** Neon (PostgreSQL)
- **Documentation:** Swagger / OpenAPI
- **Deployment:** Render

## Architecture

The project follows a clean, layered structure:

```
controller/   → REST endpoints (Auth, Activity, Recommendation)
service/      → Business logic
repository/   → Data access (Spring Data JPA)
model/        → JPA entities
dto/          → Request/Response objects, decoupled from entities
```

Request and Response DTOs are kept separate from the underlying entities to keep API contracts independent of the persistence layer.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|--------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate and receive a JWT token |
| POST | `/api/activities` | Log a new fitness activity |
| GET | `/api/activities` | Retrieve all activities for a user |
| POST | `/api/recommendation/generate` | Generate a new recommendation for a sepcific acitvity for logged in user |
| GET | `/api/recommendation/user` | Get all recommendations for a user |
| GET | `/api/recommendation/activity/{activityId}` | Get recommendations for a specific activity |

## Environment Variables

This project requires the following environment variables to run:

```
DB_URL=<your-database-url>
DB_USERNAME=<your-database-username>
DB_PASSWORD=<your-database-password>
JWT_SECRET=<your-jwt-secret-key>
JWT_EXPIRATION_TIME=<your-jwt-token-expiration-time>
```

## Running Locally

1. Clone the repository
   ```
   git clone https://github.com/ashutoshsinghmusic-lgtm/Fitness-Tracker.git
   ```
2. Set the environment variables listed above
3. Run the application
   ```
   mvn spring-boot:run
   ```
4. Access Swagger UI at `http://localhost:8080/swagger-ui/index.html`

## Author

**Ashutosh Singh**
[GitHub](https://github.com/ashutoshsinghmusic-lgtm)
