#!/usr/bin/env bash

set -e

minikube start
minikube addons enable metrics-server
minikube addons enable dashboard
minikube dashboard