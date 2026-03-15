# Todo API

A RESTful task management API built with Java 21, Spring Boot, PostgreSQL, and Docker.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4 |
| Database | PostgreSQL 16 |
| Migrations | Flyway |
| Documentation | Swagger / OpenAPI 3 |
| Testing | JUnit 5, Mockito, Testcontainers |
| Containerisation | Docker, Docker Compose |

---

## Quick Start

**Prerequisites:** Docker Desktop installed and running. Nothing else required.
```bash
git clone <your-repo-url>
cd zadanieRekrutacyjne
docker-compose up --build
```

The application starts at `http://localhost:8080`

| URL | Description |
|---|---|
| http://localhost:8080/swagger-ui.html | Interactive API docs |
| http://localhost:8080/api-docs | Raw OpenAPI JSON |

---

## API Endpoints

| Method | Endpoint | Description | Success |
|---|---|---|---|
| POST | `/api/tasks` | Create a task | 201 Created |
| GET | `/api/tasks` | List tasks (paginated, filterable) | 200 OK |
| GET | `/api/tasks/{id}` | Get task by ID | 200 OK |
| PUT | `/api/tasks/{id}` | Update task | 200 OK |
| DELETE | `/api/tasks/{id}` | Delete task | 204 No Content |

### Pagination & Filtering
```
GET /api/tasks?status=NEW&page=0&size=10&sort=createdAt,desc
```

`status` accepts: `NEW`, `IN_PROGRESS`, `DONE` (optional)  
Default page size: 10, sorted by `createdAt` descending.

### Task Object
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk and eggs",
  "status": "NEW",
  "createdAt": "2026-03-15T15:59:35.394"
}
```

### Error Responses

404 Not Found:
```json
{
  "status": 404,
  "message": "Task with id 99 not found",
  "timestamp": "2026-03-15T16:00:00"
}
```

400 Bad Request (validation):
```json
{
  "title": "Title must not be blank"
}
```

---

## Running Tests

Docker must be running — Testcontainers spins up a real PostgreSQL instance automatically.
```bash
mvn test
```

- **Unit tests** (`TaskServiceTest`) — service layer tested in isolation with Mockito
- **Integration tests** (`TaskIntegrationTest`) — full stack against a real PostgreSQL container via Testcontainers

---

## Technical Decisions

**Flyway over Hibernate DDL auto-generation**  
Schema changes are versioned SQL files under `db/migration/`. Hibernate is set to `validate` only — it checks the schema on startup but never modifies it. This prevents accidental data loss from schema drift across environments.

**DTO / Entity separation**  
`TaskRequest` and `TaskResponse` are deliberately separate from the `Task` entity. The API contract and database contract can evolve independently — a field added to the database doesn't automatically leak into the API response.

**PostgreSQL over H2**  
A real database engine is used even in development via Docker Compose. H2 compatibility mode exists but behaves differently enough from PostgreSQL to mask real bugs. Testcontainers uses the same `postgres:16-alpine` image in tests for full parity.

**Two-stage Docker build**  
The Dockerfile uses a Maven builder stage and a minimal JRE Alpine runtime stage. The final image is ~180MB instead of 600MB+. The dependency layer is cached separately from source code so incremental builds are fast.

**`@Transactional(readOnly = true)` on reads**  
Read-only transactions tell the database driver to skip transaction log writes, which improves performance under load. It also acts as a guardrail — any accidental write inside a read method will throw at runtime.

---

## Docker Commands
```bash
# Start full stack
docker-compose up --build

# Stop (preserve data)
docker-compose down

# Stop (wipe database)
docker-compose down -v
```