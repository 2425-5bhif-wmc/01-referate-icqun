#!/usr/bin/env bash

set -e

mvn clean package
java -jar target/mutiny-demo-1.0.0-SNAPSHOT-runner.jar
