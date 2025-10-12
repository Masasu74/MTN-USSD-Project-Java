#!/bin/bash

# Script to push Docker images to Docker Hub
# Usage: ./push_to_dockerhub.sh

echo "======================================"
echo "Docker Hub Push Script"
echo "======================================"
echo ""

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Error: Docker is not running. Please start Docker Desktop and try again."
    exit 1
fi

# Check if logged in to Docker Hub
if ! docker info | grep -q "Username:"; then
    echo "📝 Logging in to Docker Hub..."
    docker login
    if [ $? -ne 0 ]; then
        echo "❌ Login failed. Please try again."
        exit 1
    fi
fi

DOCKER_USERNAME=$(docker info | grep "Username:" | awk '{print $2}')
echo "✅ Logged in as: $DOCKER_USERNAME"
echo ""

# Tag images if not already tagged
echo "🏷️  Tagging images..."
docker tag ussd-project-java-backend:latest ${DOCKER_USERNAME}/ussd-backend:latest
docker tag ussd-project-java-backend:latest ${DOCKER_USERNAME}/ussd-backend:v1.0.0
docker tag ussd-project-java-frontend:latest ${DOCKER_USERNAME}/ussd-frontend:latest
docker tag ussd-project-java-frontend:latest ${DOCKER_USERNAME}/ussd-frontend:v1.0.0
echo "✅ Images tagged successfully"
echo ""

# Push backend images
echo "📤 Pushing backend images..."
echo "   - Pushing ${DOCKER_USERNAME}/ussd-backend:latest..."
docker push ${DOCKER_USERNAME}/ussd-backend:latest
if [ $? -ne 0 ]; then
    echo "❌ Failed to push backend:latest"
    exit 1
fi

echo "   - Pushing ${DOCKER_USERNAME}/ussd-backend:v1.0.0..."
docker push ${DOCKER_USERNAME}/ussd-backend:v1.0.0
if [ $? -ne 0 ]; then
    echo "❌ Failed to push backend:v1.0.0"
    exit 1
fi
echo "✅ Backend images pushed successfully"
echo ""

# Push frontend images
echo "📤 Pushing frontend images..."
echo "   - Pushing ${DOCKER_USERNAME}/ussd-frontend:latest..."
docker push ${DOCKER_USERNAME}/ussd-frontend:latest
if [ $? -ne 0 ]; then
    echo "❌ Failed to push frontend:latest"
    exit 1
fi

echo "   - Pushing ${DOCKER_USERNAME}/ussd-frontend:v1.0.0..."
docker push ${DOCKER_USERNAME}/ussd-frontend:v1.0.0
if [ $? -ne 0 ]; then
    echo "❌ Failed to push frontend:v1.0.0"
    exit 1
fi
echo "✅ Frontend images pushed successfully"
echo ""

echo "======================================"
echo "✅ All images pushed successfully!"
echo "======================================"
echo ""
echo "Your images are now available at:"
echo "  - https://hub.docker.com/r/${DOCKER_USERNAME}/ussd-backend"
echo "  - https://hub.docker.com/r/${DOCKER_USERNAME}/ussd-frontend"
echo ""
echo "To pull these images on another machine:"
echo "  docker pull ${DOCKER_USERNAME}/ussd-backend:v1.0.0"
echo "  docker pull ${DOCKER_USERNAME}/ussd-frontend:v1.0.0"
echo ""

