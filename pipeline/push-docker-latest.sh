#!/bin/bash

docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
docker tag jimbrighter/planner-svc:${DOCKER_TAG} jimbrighter/planner-svc:latest
docker tag jimbrighter/planner-auth:${DOCKER_TAG} jimbrighter/planner-auth:latest
docker tag jimbrighter/planner-db:${DOCKER_TAG} jimbrighter/planner-db:latest
docker tag jimbrighter/planner-ui:${DOCKER_TAG} jimbrighter/planner-ui:latest

docker push jimbrighter/planner-svc:latest
docker push jimbrighter/planner-auth:latest
docker push jimbrighter/planner-db:latest
docker push jimbrighter/planner-ui:latest
