#!/bin/bash
cd ..
mvn clean install -TC2 -Dmaven.test.skip=true
cd docker
rm conbeeii-service-0.0.1-SNAPSHOT.jar
cp ../target/conbeeii-service-0.0.1-SNAPSHOT.jar .
export DOCKER_HOST=192.168.1.181
docker build -t conbeeii-service .
docker stop conbeeII-service
docker rm ha-service
docker run -d --link test-mysql --name conbeeii-service conbeeii-service:latest
docker logs -f conbeeii-service | less
