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
- How to split an app into layers. The controller handles web requests, the service holds the logic, and the repository talks to the database. I built it from the bottom up, starting with the data and ending with the endpoints.
- How to use Spring Boot to build a REST API with GET, POST, PUT and DELETE endpoints, and how to use Gradle to manage the project's libraries and build it.
- How to connect a Java app to PostgreSQL with Spring Data JPA, so I didn't have to write SQL by hand.
- How to check input and handle errors, so bad requests get a clear 400 or 404 message instead of a crash.
- How to write tests: unit tests with JUnit and Mockito, and integration tests with MockMvc, using an in-memory H2 database so the real data is never touched.
- How to use Docker and Docker Compose to run the app and database together with one command, and how volumes keep the data after the containers stop.
- How to keep secrets like the database password out of the code with environment variables and a .env file that Git ignores.
- How to use Git and GitHub: committing, pushing, cloning, undoing changes, and testing a fresh clone to make sure it works for someone else.
- Money is stored as BigDecimal and not double, because double can give rounding errors.