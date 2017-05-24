#!/bin/bash
nohup java -jar /root/ci-eureka-server.jar > ci-eureka-server.log&
echo 'ci-eureka-server started.' 
nohup java -jar /root/ci-storage.jar > ci-storage.log&
echo 'ci-storage started.'
nohup java -jar /root/ci-web.jar > ci-web.log&
echo 'ci-web started.'