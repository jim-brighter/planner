#!/bin/bash

docker build -t jimbrighter/planner-svc:${DOCKER_TAG} -f planner-svc/Dockerfile planner-svc
docker build -t jimbrighter/planner-auth:${DOCKER_TAG} -f planner-auth/Dockerfile planner-auth
docker build -t jimbrighter/planner-db:${DOCKER_TAG} -f planner-db/Dockerfile planner-db
docker build -t jimbrighter/planner-ui:${DOCKER_TAG} -f planner-ui/Dockerfile planner-ui/staging
