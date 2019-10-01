#!/bin/bash

chown jenkins:jenkins ~/.ssh/id_rsa
chmod 700 ~/.ssh/id_rsa
eval $(ssh-agent -s)
ssh-add ~/.ssh/id_rsa
echo > ~/.ssh/known_hosts

ssh -o StrictHostKeyChecking=no jbrighter@jimandfangzhuo.com 'sh redeploy.sh'
