#!/bin/bash

docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
docker push jimbrighter/planner-svc:latest
docker push jimbrighter/planner-auth:latest
docker push jimbrighter/planner-db:latest
docker push jimbrighter/planner-ui:latest
