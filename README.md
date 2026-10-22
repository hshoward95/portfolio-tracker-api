# Portfolio Tracker API

A Spring Boot REST API for keeping track of stock holdings. It uses PostgreSQL and runs in Docker.

## Run it
You need Docker installed.

cp .env.example .env
docker compose up --build

On Windows PowerShell, use `copy .env.example .env` for the first line.
The API runs at http://localhost:8080.

## Endpoints
GET /api/holdings, GET /api/holdings/{id}, POST /api/holdings, PUT /api/holdings/{id}, DELETE /api/holdings/{id}, and GET /api/portfolio/summary (total invested and the amount per symbol)

## Tests
./gradlew test

## Things I learned
- 