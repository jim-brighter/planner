#!/bin/bash

./gradlew clean build --no-daemon

cd planner-ui
npm i
npm run buildProd
cp nginx.conf staging/
