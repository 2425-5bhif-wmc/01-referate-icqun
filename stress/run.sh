#!/usr/bin/env bash

set -e
shift

mvn clean package
java -cp target/stress-1.0.jar at.htl.Stress "$@"
