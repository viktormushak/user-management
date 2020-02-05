#!/bin/bash
while [[ $(curl --write-out %{http_code} --silent --output /dev/null http://${REGISTRY_SERVER_HOST}:8761) != 200 ]];
do
    echo "Waiting for eureka service on http://${REGISTRY_SERVER_HOST}:8761 ..."
    sleep 5
done
    echo "=================================================================="
    echo "Connected to eureka service"
    echo "=================================================================="
    echo "Starting user-management ..."

    java -Dspring.profiles.active=prod -jar vertex-user-management.jar