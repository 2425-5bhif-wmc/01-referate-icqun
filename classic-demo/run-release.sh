#!/usr/bin/env bash

set -e

mvn clean package
java -jar target/classic-demo-1.0.0-SNAPSHOT-runner.jar
