#!/usr/bin/env bash

mvn clean package
java -cp target/stress-1.0.jar at.htl.Stress
