# MTN USSD Project (Java)

Comprehensive USSD solution built with a Spring Boot backend and a Next.js client. The project integrates with MTN's USSD gateways and provides tooling for local development, database provisioning, and deployment workflows.

## Repository Layout

- `server/` – Spring Boot application that exposes REST and SOAP endpoints, handles business logic, and persists data via MySQL.
- `client/` – Next.js front-end for administrative workflows and bundle management.
- `docker-compose.yml` – Spins up the MySQL database and supporting services required by the backend.
- `*.sql` & `setup_database.sh` – Convenience scripts for creating and seeding the database.
- `docs/*.md` – Guides for testing, deployment, and third-party integrations.

## Prerequisites

- Java 21 or newer
- Node.js 20+
- Maven 3.9+
- Docker (optional, for running supporting services)
- MySQL 8+ (or Docker Compose services)

## Getting Started

1. Clone the repository (including the client submodule if applicable):
   ```bash
   git clone https://github.com/Masasu74/MTN-USSD-Project-Java.git
   ```
2. Provision the database:
   ```bash
   cd MTN-USSD-Project-Java
   ./setup_database.sh
   ```
   or manually execute `CREATE_DATABASE.sql` followed by `COMPLETE_DATABASE_SETUP.sql`.
3. Start the backend:
   ```bash
   cd server
   ./mvnw spring-boot:run
   ```
4. Start the frontend:
   ```bash
   cd ../client
   npm install
   npm run dev
   ```

## Environment Variables

Create a `.env` file in both `server` and `client` folders based on the provided `.env.example` templates (if available). Ensure the backend database credentials match your local MySQL instance or Docker Compose configuration.

## Testing

- Backend tests:
  ```bash
  cd server
  ./mvnw test
  ```
- Frontend tests (if configured):
  ```bash
  cd client
  npm test
  ```

## Deployment

Refer to `DOCKER_DEPLOYMENT_GUIDE.md`, `DOCKER_README.md`, and `MTN_DEPLOYMENT_READINESS.md` for guidance on containerization and production deployment. Scripts like `push_to_dockerhub.sh` assist with Docker image management.

## Contributing

1. Create a feature branch from `main`.
2. Make your changes and ensure all tests pass.
3. Submit a pull request with clear descriptions and references to any related issues.

## License

This project is maintained by the BMT team. Licensing details will be provided here once finalized.


