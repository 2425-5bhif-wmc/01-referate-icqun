#!/usr/bin/env bash
minikube start
minikube addons enable metrics-server
minikube addons enable dashboard
minikube dashboard