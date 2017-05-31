FROM java:8
ADD ci-eureka-server/target/ci-eureka-server.jar /root/ci-eureka-server.jar
ADD ci-storage/target/ci-storage.jar /root/ci-storage.jar
ADD ci-web/target/ci-web.jar /root/ci-web.jar
ADD start.sh /root/start.sh
EXPOSE 9998
EXPOSE 9999
EXPOSE 11111