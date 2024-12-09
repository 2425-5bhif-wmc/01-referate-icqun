#!/usr/bin/env bash

set -e

_dir=$(dirname $0)

echo -e "\napplying deployments..."
kubectl apply -f ${_dir}/appsrv-classic.yaml
kubectl apply -f ${_dir}/appsrv-mutiny.yaml
kubectl apply -f ${_dir}/postgres.yaml

echo -e "\nrestarting deployments..."
kubectl apply -f ${_dir}/appsrv-classic.yaml
kubectl rollout restart deployment appsrv-classic
kubectl rollout restart deployment appsrv-mutiny

echo -e "\nwaiting for deployments to become available..."
kubectl wait --for=condition=available --timeout=600s deployment/appsrv-classic
kubectl wait --for=condition=available --timeout=600s deployment/appsrv-mutiny

echo -e "\nwaiting for pods to come online..."
kubectl wait --for=condition=ready --timeout=600s pod --all

echo -e "\nforwarding ports...."
kubectl port-forward deployment/appsrv-classic 8080:8080 & \
kubectl port-forward deployment/appsrv-mutiny 8081:8081 &

echo -e "\nPress CTRL-C to exit"
wait