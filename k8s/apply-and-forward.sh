#!/usr/bin/env bash

set -e

_dir=$(dirname $0)

kubectl apply -f ${_dir}/appsrv-classic.yaml
kubectl apply -f ${_dir}/appsrv-mutiny.yaml
kubectl apply -f ${_dir}/postgres.yaml

kubectl rollout restart deployment appsrv-classic
kubectl rollout restart deployment appsrv-mutiny

kubectl port-forward deployment/appsrv-classic 8080:8080 & \
kubectl port-forward deployment/appsrv-mutiny 8081:8081 &

echo "Press CTRL-C to exit"
wait