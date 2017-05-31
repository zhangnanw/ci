#VERSION 1.0
FROM java
MAINTAINER  n.zhang  273105667@qq.com 
RUN mvn clean compile package
ADD target/ci-web.jar /root/app.jar
EXPOSE  9999
CMD  ["java","-jar","/root/app.jar"]