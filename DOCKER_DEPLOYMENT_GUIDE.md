# Docker Deployment Guide

This guide explains how to build, run, and deploy the USSD Project using Docker.

## 🐳 Project Architecture

This project consists of three main services:
- **MySQL Database** - Stores all application data
- **Spring Boot Backend** - REST API server (Port 8080)
- **Next.js Frontend** - Web interface (Port 3000)

## 📋 Prerequisites

- Docker Desktop installed and running
- Docker Hub account (for publishing images)
- Git (for version control)

## 🚀 Quick Start

### 1. Build and Run All Services

```bash
# Build and start all services
docker-compose up -d --build

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Stop and remove volumes (clean slate)
docker-compose down -v
```

### 2. Access the Application

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **MySQL**: localhost:3307 (external port)

## 🏗️ Building Individual Images

### Backend (Spring Boot)

```bash
cd server
docker build -t ussd-backend:latest .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3307/mtn_ussd \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=rootpassword \
  ussd-backend:latest
```

### Frontend (Next.js)

```bash
cd client
docker build -t ussd-frontend:latest .
docker run -p 3000:3000 \
  -e NEXT_PUBLIC_API_URL=http://localhost:8080 \
  ussd-frontend:latest
```

### Database (MySQL)

```bash
docker run -d \
  --name ussd-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=mtn_ussd \
  -p 3307:3306 \
  -v mysql_data:/var/lib/mysql \
  -v $(pwd)/COMPLETE_DATABASE_SETUP.sql:/docker-entrypoint-initdb.d/init.sql \
  mysql:8.0
```

## 📦 Publishing to Docker Hub

### 1. Login to Docker Hub

```bash
docker login
# Enter your Docker Hub username and password
```

### 2. Tag Images

Replace `YOUR_DOCKERHUB_USERNAME` with your actual Docker Hub username:

```bash
# Tag backend
docker tag ussd-backend:latest YOUR_DOCKERHUB_USERNAME/ussd-backend:latest
docker tag ussd-backend:latest YOUR_DOCKERHUB_USERNAME/ussd-backend:v1.0.0

# Tag frontend
docker tag ussd-frontend:latest YOUR_DOCKERHUB_USERNAME/ussd-frontend:latest
docker tag ussd-frontend:latest YOUR_DOCKERHUB_USERNAME/ussd-frontend:v1.0.0
```

### 3. Push Images

```bash
# Push backend
docker push YOUR_DOCKERHUB_USERNAME/ussd-backend:latest
docker push YOUR_DOCKERHUB_USERNAME/ussd-backend:v1.0.0

# Push frontend
docker push YOUR_DOCKERHUB_USERNAME/ussd-frontend:latest
docker push YOUR_DOCKERHUB_USERNAME/ussd-frontend:v1.0.0
```

### 4. Using Published Images

Update your `docker-compose.yml` to use the published images:

```yaml
services:
  backend:
    image: YOUR_DOCKERHUB_USERNAME/ussd-backend:latest
    # Remove the 'build' section
    container_name: ussd-backend
    # ... rest of configuration

  frontend:
    image: YOUR_DOCKERHUB_USERNAME/ussd-frontend:latest
    # Remove the 'build' section
    container_name: ussd-frontend
    # ... rest of configuration
```

## 🔧 Configuration

### Environment Variables

#### Backend
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SPRING_JPA_HIBERNATE_DDL_AUTO` - Hibernate DDL mode (update/create/validate)

#### Frontend
- `NEXT_PUBLIC_API_URL` - Backend API URL

#### Database
- `MYSQL_ROOT_PASSWORD` - MySQL root password
- `MYSQL_DATABASE` - Database name to create on startup

### Volumes

- `mysql_data` - Persistent MySQL data storage

### Networks

- `ussd-network` - Bridge network connecting all services

## 🔍 Troubleshooting

### View Service Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f mysql
```

### Check Container Status

```bash
docker-compose ps
```

### Restart a Service

```bash
docker-compose restart backend
docker-compose restart frontend
docker-compose restart mysql
```

### Clean Build (if facing issues)

```bash
# Stop all services and remove volumes
docker-compose down -v

# Remove old images
docker rmi ussd-backend:latest ussd-frontend:latest

# Rebuild everything
docker-compose up -d --build
```

### Database Connection Issues

1. Check if MySQL is healthy:
   ```bash
   docker-compose ps
   ```

2. Verify MySQL is accepting connections:
   ```bash
   docker exec -it ussd-mysql mysql -uroot -prootpassword -e "SELECT 1"
   ```

3. Check backend logs for connection errors:
   ```bash
   docker-compose logs backend | grep -i error
   ```

### Frontend Build Issues

1. Clear Next.js cache:
   ```bash
   cd client
   rm -rf .next node_modules
   ```

2. Rebuild the frontend image:
   ```bash
   docker-compose up -d --build frontend
   ```

## 📊 Health Checks

The MySQL service includes a health check that ensures the database is ready before starting dependent services:

```yaml
healthcheck:
  test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
  timeout: 20s
  retries: 10
```

## 🌐 Production Deployment

### Security Best Practices

1. **Change Default Passwords**: Update MySQL root password in production
2. **Use Secrets**: Store sensitive data in Docker secrets or environment files
3. **Enable HTTPS**: Use a reverse proxy (Nginx/Traefik) with SSL certificates
4. **Limit Exposed Ports**: Only expose necessary ports
5. **Regular Updates**: Keep base images updated

### Example Production docker-compose.yml

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: ussd-mysql-prod
    environment:
      MYSQL_ROOT_PASSWORD_FILE: /run/secrets/mysql_root_password
      MYSQL_DATABASE: mtn_ussd
    secrets:
      - mysql_root_password
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - ussd-network
    restart: unless-stopped

  backend:
    image: YOUR_DOCKERHUB_USERNAME/ussd-backend:v1.0.0
    container_name: ussd-backend-prod
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/mtn_ussd
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD_FILE: /run/secrets/mysql_root_password
    secrets:
      - mysql_root_password
    depends_on:
      - mysql
    networks:
      - ussd-network
    restart: unless-stopped

  frontend:
    image: YOUR_DOCKERHUB_USERNAME/ussd-frontend:v1.0.0
    container_name: ussd-frontend-prod
    environment:
      NEXT_PUBLIC_API_URL: https://api.yourdomain.com
    depends_on:
      - backend
    networks:
      - ussd-network
    restart: unless-stopped

  nginx:
    image: nginx:alpine
    container_name: ussd-nginx
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
      - ./ssl:/etc/nginx/ssl
    depends_on:
      - frontend
      - backend
    networks:
      - ussd-network
    restart: unless-stopped

secrets:
  mysql_root_password:
    file: ./secrets/mysql_root_password.txt

networks:
  ussd-network:
    driver: bridge

volumes:
  mysql_data:
```

## 📝 Useful Commands

```bash
# Build without cache
docker-compose build --no-cache

# View resource usage
docker stats

# Execute command in container
docker exec -it ussd-backend bash
docker exec -it ussd-mysql mysql -uroot -prootpassword mtn_ussd

# Copy files from container
docker cp ussd-backend:/app/logs ./backend-logs

# Export/Import Database
docker exec ussd-mysql mysqldump -uroot -prootpassword mtn_ussd > backup.sql
docker exec -i ussd-mysql mysql -uroot -prootpassword mtn_ussd < backup.sql

# Prune unused Docker resources
docker system prune -a --volumes
```

## 📄 License

This project is part of the MTN USSD Application.

