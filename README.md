# FitnessTracker-Project

A Spring Boot REST API for tracking fitness activities and generating AI-powered recommendations, built with a layered architecture (Controller-Service-Repository-DTO). Features JWT-based authentication and authorization, AI-generated fitness insights via Google Gemini, Spring Data JPA for persistence, and Swagger documentation. Deployed live on Render.

## 🌐 Live Demo

Swagger UI: https://fitness-project-latest-chws.onrender.com/swagger-ui/index.html

## 📸 Screenshots

<img width="1905" height="893" alt="Screenshot 2026-06-27 195643" src="https://github.com/user-attachments/assets/cee82517-b864-4416-8425-c62ec9caafa6" />
<img width="1897" height="904" alt="Screenshot 2026-06-27 195712" src="https://github.com/user-attachments/assets/678766fe-3704-4fab-a378-4c779d5498e2" />
<img width="1612" height="899" alt="Screenshot 2026-06-26 145220" src="https://github.com/user-attachments/assets/2c559595-543e-4a1d-a59d-ab36428dc28e" />
<img width="1310" height="642" alt="Screenshot 2026-06-26 150104" src="https://github.com/user-attachments/assets/66c2b42c-9768-4073-bd06-378da1f11dbd" />




## Features

- **User Authentication & Authorization** — Secure registration and login using Spring Security and JWT tokens
- **Activity Tracking** — Log fitness activities (Running, Cycling, Yoga, HIIT, Swimming, and more) with duration, calories burned, and custom metrics
- **AI-Powered Recommendations** — Automatically generates personalized improvement tips, suggestions, and safety advice for every logged activity, using Google's Gemini API
- **Resource-Level Authorization** — Users can only access their own activities and recommendations, even with a valid token
- **API Documentation** — Interactive API docs via Swagger
- **Role-Based Access** — Supports USER and ADMIN roles

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Security:** Spring Security, JWT
- **AI Integration:** Google Gemini API (via Spring `RestClient`)
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** Neon (PostgreSQL)
- **Documentation:** Swagger
- **Deployment:** Render

## Architecture

The project follows a clean, layered structure:

```
controller/   → REST endpoints (Auth, Activity, Recommendation)
service/      → Business logic (including AI recommendation generation)
repository/   → Data access (Spring Data JPA)
model/        → JPA entities
dto/          → Request/Response objects, decoupled from entities
```

Request and Response DTOs are kept separate from the underlying entities to keep API contracts independent of the persistence layer.

## How AI Recommendations Work

When a user logs a new activity, the application automatically sends the activity's details (type, duration, calories burned) to Google's Gemini API. Gemini analyzes this data and returns personalized improvements, suggestions, and safety tips, which are parsed and saved alongside the activity — no extra step required from the user.

If the AI service is temporarily unavailable, the activity is still saved successfully; only the recommendation generation is skipped for that request.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|--------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate and receive a JWT token |
| POST | `/api/activities` | Log a new fitness activity (automatically triggers AI recommendation generation) |
| GET | `/api/activities` | Retrieve all activities for the logged-in user |
| GET | `/api/recommendation/user` | Get all recommendations for the logged-in user |
| GET | `/api/recommendation/activity/{activityId}` | Get recommendations for a specific activity |

## Environment Variables

This project requires the following environment variables to run:

```
DB_URL=<your-database-url>
DB_USERNAME=<your-database-username>
DB_PASSWORD=<your-database-password>
JWT_SECRET=<your-jwt-secret-key>
JWT_EXPIRATION_TIME=<your-jwt-token-expiration-time>
GEMINI_API_KEY=<your-google-gemini-api-key>
```

## Running Locally

1. Clone the repository
   ```
   git clone https://github.com/justashutoshsingh/Fitness-Tracker.git
   ```
2. Set the environment variables listed above
3. Run the application
   ```
   mvn spring-boot:run
   ```
4. Access Swagger UI at `http://localhost:8080/swagger-ui/index.html`

## Author

Ashutosh Singh
[GitHub](https://github.com/justashutoshsingh)
