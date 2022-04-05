#!/bin/bash
cd .. && cd ApiGatewayService-java && mvn clean install && docker build -t petstore-gateway-java .

cd .. && cd .. && cd pet-service-v1-java/pet-service-v1-java && mvn clean install && docker build -t petstore-pet-java .

cd .. && cd .. && cd user-service-v1-java/user-service-v1-java && mvn clean install && docker build -t petstore-user-java .

cd .. && cd .. && cd store-service-v1-java/store-service-v1-java && mvn clean install && docker build -t petstore-store-java .

docker run --rm --add-host=host.docker.internal:host-gateway -p 8080:8080 petstore-gateway-java &
docker run --rm --add-host=host.docker.internal:host-gateway -p 8081:8081 petstore-pet-java &
docker run --rm --add-host=host.docker.internal:host-gateway -p 8082:8082 petstore-user-java &
docker run --rm --add-host=host.docker.internal:host-gateway -p 8083:8083 petstore-store-java &
wait
