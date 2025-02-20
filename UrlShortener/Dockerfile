
FROM openjdk:21

MAINTAINER Nata.cool

WORKDIR /app

COPY target/*.jar app.jar

## ENTRYPOINT ["java", "-Dspring"]
## ENTRYPOINT ["java", "-Dspring.profile.active=dev", "-jar", "app.jar"]
ENTRYPOINT ["java", "-jar", "app.jar"]
# ENTRYPOINT ["java","-jar","app.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
# EXPOSE 8090


