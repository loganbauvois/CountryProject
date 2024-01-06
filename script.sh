#!/bin/bash

# Package le back ms-user
cd ./Back-Flag-User-Service
mvn package
cd ..

# Package le back ms-countries
cd ./countries-service
mvn package
cd ..

docker-compose up