FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /opt/application

COPY pom.xml .

RUN mvn verify clean --fail-never

COPY . .

RUN mvn -B package

FROM eclipse-temurin:21-jre

RUN mkdir -p /opt/application
COPY --from=builder /opt/application/target/*-runner.jar /opt/application/backend-classic.jar
WORKDIR /opt/application
CMD [ "java", "-jar", "backend-classic.jar" ]