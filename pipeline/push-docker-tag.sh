#!/bin/bash

docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
docker push jimbrighter/planner-svc:${DOCKER_TAG}
docker push jimbrighter/planner-auth:${DOCKER_TAG}
docker push jimbrighter/planner-db:${DOCKER_TAG}
docker push jimbrighter/planner-ui:${DOCKER_TAG}
