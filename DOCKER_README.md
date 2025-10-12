# 🐳 Docker Setup for MTN USSD Application

## ✅ What's Been Done

Your MTN USSD application has been successfully dockerized! Here's what has been completed:

### 1. Docker Images Built
- ✅ **Backend (Spring Boot)**: `ussd-project-java-backend:latest`
- ✅ **Frontend (Next.js)**: `ussd-project-java-frontend:latest`
- ✅ **Database (MySQL)**: Using official `mysql:8.0` image

### 2. Docker Hub Images Tagged
- ✅ `masasu74/ussd-backend:latest`
- ✅ `masasu74/ussd-backend:v1.0.0`
- ✅ `masasu74/ussd-frontend:latest`
- ✅ `masasu74/ussd-frontend:v1.0.0`

### 3. Services Running

All services are currently running and accessible:

| Service | Status | Port | URL |
|---------|--------|------|-----|
| **MySQL** | ✅ Running | 3308 | `localhost:3308` |
| **Backend** | ✅ Running | 8080 | http://localhost:8080 |
| **Frontend** | ✅ Running | 3000 | http://localhost:3000 |

## 🚀 Quick Commands

### Start All Services
```bash
docker-compose up -d
```

### Stop All Services
```bash
docker-compose down
```

### View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f mysql
```

### Check Status
```bash
docker-compose ps
```

### Rebuild Images
```bash
docker-compose up -d --build
```

### Clean Restart (Remove volumes)
```bash
docker-compose down -v
docker-compose up -d
```

## 📦 Pushing to Docker Hub

Due to network timeouts, a push script has been created for you. When your network connection is stable, run:

```bash
./push_to_dockerhub.sh
```

This script will:
1. Verify Docker is running
2. Log you into Docker Hub (if needed)
3. Tag all images with your Docker Hub username
4. Push both backend and frontend images with `latest` and `v1.0.0` tags
5. Display links to your Docker Hub repositories

## 🌐 Accessing the Application

### Frontend Dashboard
Open your browser and navigate to:
```
http://localhost:3000
```

You should see the MTN USSD Dashboard with:
- Sidebar navigation
- Bundle management interface
- Purchase history
- Category management

### Backend API
The REST API is available at:
```
http://localhost:8080/api/bundles
```

Test it with curl:
```bash
curl http://localhost:8080/api/bundles
```

### Database
Connect to MySQL:
```bash
# Using docker exec
docker exec -it ussd-mysql mysql -uroot -prootpassword mtn_ussd

# Or use any MySQL client
Host: localhost
Port: 3308
User: root
Password: rootpassword
Database: mtn_ussd
```

## 📋 Environment Configuration

### Backend Environment Variables
Located in `docker-compose.yml`:
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_JPA_HIBERNATE_DDL_AUTO`: Hibernate DDL mode

### Frontend Environment Variables
- `NEXT_PUBLIC_API_URL`: Backend API URL (currently `http://localhost:8080`)

### Database Environment Variables
- `MYSQL_ROOT_PASSWORD`: MySQL root password
- `MYSQL_DATABASE`: Database name (mtn_ussd)

## 🔧 Troubleshooting

### Port Already in Use
If you get a "port already in use" error:
```bash
# Find what's using the port (example for 8080)
lsof -i :8080

# Kill the process or change the port in docker-compose.yml
```

### Database Not Ready
If the backend fails to connect to the database:
```bash
# Check MySQL health
docker-compose ps

# View MySQL logs
docker-compose logs mysql

# Restart services
docker-compose restart backend
```

### Frontend Build Issues
If the frontend fails to build:
```bash
# Rebuild without cache
docker-compose build --no-cache frontend
docker-compose up -d frontend
```

### Network Issues
If you experience network timeouts when pushing to Docker Hub:
```bash
# Wait for a stable connection and run
./push_to_dockerhub.sh

# Or push manually
docker push masasu74/ussd-backend:latest
docker push masasu74/ussd-frontend:latest
```

## 📁 Docker Files Structure

```
.
├── docker-compose.yml          # Main orchestration file
├── DOCKER_DEPLOYMENT_GUIDE.md  # Comprehensive deployment guide
├── DOCKER_README.md            # This file
├── push_to_dockerhub.sh        # Script to push images
├── server/
│   ├── Dockerfile              # Backend Docker configuration
│   └── .dockerignore          # Files to exclude from backend image
└── client/
    ├── Dockerfile              # Frontend Docker configuration
    └── .dockerignore          # Files to exclude from frontend image
```

## 🎯 Next Steps

### 1. Push to Docker Hub
When your network is stable:
```bash
./push_to_dockerhub.sh
```

### 2. Pull on Another Machine
Once pushed, you can pull and run on any machine:
```bash
# Pull images
docker pull masasu74/ussd-backend:v1.0.0
docker pull masasu74/ussd-frontend:v1.0.0

# Update docker-compose.yml to use remote images
# Then run
docker-compose up -d
```

### 3. Deploy to Production
For production deployment:
1. Use environment-specific configuration
2. Set up proper secrets management
3. Configure SSL/HTTPS with a reverse proxy (Nginx/Traefik)
4. Set up monitoring and logging
5. Configure automatic restarts
6. Use Docker Swarm or Kubernetes for orchestration

See `DOCKER_DEPLOYMENT_GUIDE.md` for detailed production deployment instructions.

## 📊 Resource Usage

Current container resource usage:
```bash
# View real-time stats
docker stats ussd-mysql ussd-backend ussd-frontend
```

Expected usage:
- **MySQL**: ~200-400MB RAM
- **Backend**: ~400-600MB RAM
- **Frontend**: ~50-150MB RAM

## 🛡️ Security Considerations

### Development (Current Setup)
- ✅ Services communicate via internal Docker network
- ✅ Only necessary ports exposed to host
- ⚠️ Using default passwords (CHANGE FOR PRODUCTION!)

### Production Recommendations
1. **Change all default passwords**
2. **Use Docker secrets** for sensitive data
3. **Enable HTTPS** with proper SSL certificates
4. **Implement rate limiting**
5. **Add authentication** and authorization
6. **Regular security updates** for base images
7. **Network isolation** with separate Docker networks
8. **Volume backups** and disaster recovery plan

## 📚 Additional Resources

- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Docker Hub](https://hub.docker.com/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Next.js Docker Deployment](https://nextjs.org/docs/deployment#docker-image)

## 🎉 Summary

Your application is now fully containerized and running! Here's what you can do:

1. **Access the app**: http://localhost:3000
2. **Test the API**: http://localhost:8080/api/bundles
3. **View logs**: `docker-compose logs -f`
4. **Push to Docker Hub**: `./push_to_dockerhub.sh` (when network is stable)
5. **Deploy anywhere**: Use the pushed images to deploy on any Docker-enabled server

---

**Need help?** Check the comprehensive guide in `DOCKER_DEPLOYMENT_GUIDE.md`

**Questions?** Review the troubleshooting section above or check container logs.

✨ Happy containerizing! 🐳

