#!/usr/bin/env bash

set -e

mvn clean package
java -cp target/stress-1.0.jar at.htl.Stress "$1" "$2"
