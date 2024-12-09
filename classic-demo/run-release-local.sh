#!/usr/bin/env bash

set -e

mvn clean package
QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://localhost:5432/db java -jar target/classic-demo-1.0.0-SNAPSHOT-runner.jar
