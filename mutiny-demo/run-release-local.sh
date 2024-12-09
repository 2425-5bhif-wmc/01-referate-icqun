#!/usr/bin/env bash

set -e

mvn clean package
QUARKUS_DATASOURCE_REACTIVE_URL=vertx-reactive:postgresql://localhost:5432/db java -jar target/mutiny-demo-1.0.0-SNAPSHOT-runner.jar
