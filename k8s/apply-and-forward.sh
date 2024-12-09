#!/usr/bin/env bash

set -e

_dir=$(dirname $0)

kubectl apply -f ${_dir}/appsrv-classic.yaml
kubectl apply -f ${_dir}/appsrv-mutiny.yaml
kubectl apply -f ${_dir}/postgres.yaml

kubectl rollout restart deployment appsrv-classic
kubectl rollout restart deployment appsrv-mutiny

#
#while : ; do
#  containers=$(kubectl get pods --sort-by=.status.startTime | grep appsrv-classic | cut -d' ' -f1 | tac)
#  c=$(echo $containers| cut --delimiter " " --fields 1)
#  echo "Waiting for $c to come online"
#  kubectl get pod $c | grep Running >/dev/null
#  res=$?
#
#  [[ $res != 0 ]] || break
#done
#
#echo "forwarding $c"
#kubectl port-forward $c 8080:8080
