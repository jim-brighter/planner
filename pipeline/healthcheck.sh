#!/bin/bash

attempts=0
max_attempts=36

url="https://jimandfangzhuo.com:8443/actuator/health"

until $(curl -k --output /dev/null --silent --head --fail $url); do

  if [ ${attempts} -eq ${max_attempts} ]; then
    echo "App failed to respond in a timely manner!"
    exit 1
  fi

  attempts=$(($attempts+1))
  echo "Waiting for app to respond at $url ..."
  sleep 10
done
