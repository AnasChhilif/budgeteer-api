#!/bin/bash

# Step 1: Build the Kotlin project using Gradle
echo "Building the Kotlin project..."
./gradlew build

if [ $? -ne 0 ]; then
  echo "Gradle build failed. Exiting..."
  exit 1
fi

echo "Gradle build completed successfully."

# Step 2: Build the Docker image for the Kotlin application
echo "Building the Docker image for the Kotlin application..."
podman build -t budgeteer-api .

if [ $? -ne 0 ]; then
  echo "Docker image build failed. Exiting..."
  exit 1
fi

echo "Docker image build completed successfully."

# Step 3: Run the services using podman-compose
echo "Starting the services with podman-compose..."
podman compose up

if [ $? -ne 0 ]; then
  echo "Failed to start services with podman-compose. Exiting..."
  exit 1
fi

echo "Services started successfully."
