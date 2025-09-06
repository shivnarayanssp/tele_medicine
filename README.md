# Telemedicine Backend Service
A simplified backend for a telemedicine app with JWT authentication, doctor-patient roles, appointments, and real-time doctor status updates.

Features

User Registration & Login with JWT

Doctor & Patient Roles with CRUD operations

Appointment Management per user

Real-Time Doctor Status via WebSocket (online/offline)

Swagger UI for API documentation

Tech Stack

Java, Spring Boot, JWT, WebSocket, MySQL/PostgreSQL/H2, Swagger

Quick Start

Clone repo:
git clone <repo-url>
cd telemedicine-backend

Configure DB in application.properties

Build & run:
mvn clean install
mvn spring-boot:run

Swagger UI: http://localhost:8080/swagger-ui.html

API Endpoints

/auth/register – Register user

/auth/login – Login & get JWT

/doctors – CRUD for doctors

/patients – CRUD for patients

/appointments – Create & list appointments

/ws/status – WebSocket for doctor status
