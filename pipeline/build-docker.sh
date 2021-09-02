#!/bin/bash

docker build -t jimbrighter/planner-svc:latest -f planner-svc/Dockerfile planner-svc
docker build -t jimbrighter/planner-auth:latest -f planner-auth/Dockerfile planner-auth
docker build -t jimbrighter/planner-db:latest -f planner-db/Dockerfile planner-db
docker build -t jimbrighter/planner-ui:latest -f planner-ui/Dockerfile planner-ui/staging
