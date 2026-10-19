# Portfolio Tracker API

A REST API for tracking stock holdings, built with Spring Boot and PostgreSQL. Create, read, update and delete holdings, and get a summary of how much is invested in each symbol.

## Tech stack

- Java 21, Spring Boot 4, Spring Data JPA (Hibernate)
- PostgreSQL
- Gradle
- JUnit 5, Mockito and MockMvc (unit and integration tests, run on in-memory H2)
- Docker and Docker Compose

## Architecture

Controller (HTTP) → Service (business logic) → Repository (database access) → PostgreSQL

Input is validated with Bean Validation, and errors return consistent JSON (400 for invalid input, 404 for missing holdings).

## Run it with Docker

Requires Docker only.

```
cp .env.example .env
docker compose up --build
```

On Windows PowerShell, use `copy .env.example .env` for the first line. The API is then at http://localhost:8080.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | /api/holdings | List all holdings |
| GET | /api/holdings/{id} | Get one holding |
| POST | /api/holdings | Create a holding (201) |
| PUT | /api/holdings/{id} | Update a holding |
| DELETE | /api/holdings/{id} | Delete a holding (204) |
| GET | /api/portfolio/summary | Total invested and amount per symbol |

Example:

```
curl -X POST http://localhost:8080/api/holdings -H "Content-Type: application/json" -d '{"symbol":"AAPL","shares":10,"costPerShare":150.25}'
```

## Run the tests

```
./gradlew test
```

## Design notes

- Money is stored as `BigDecimal`, never `double`, to avoid rounding errors.
- Database credentials come from environment variables, so no secrets are in the repo.
- Tests use an in-memory database, so they never touch real data.