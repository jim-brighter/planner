#!/bin/bash

cd planner-ui
npm i
npm run buildProd
cp nginx.conf staging/
