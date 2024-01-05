FROM openjdk:21

LABEL authors="theolorthioir"

ENV SERVER_PORT=8081

ENV DBHOST=mongodb

ARG JAR_FILE=target/countries-service-*.jar
ARG INIT_DB=src/main/resources/initdb.json

# Ces commandes empêchent l'exécution du Dockerfile sur Mac AppleSilicon
#RUN addgroup --system suer \
#    && adduser --system --ingroup user user

#USER user

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

COPY ${INIT_DB} .

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","app.jar"]