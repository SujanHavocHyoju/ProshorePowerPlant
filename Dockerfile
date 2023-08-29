FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar ProshorePowerPlant-v1.jar
ENTRYPOINT ["java","-jar","/ProshorePowerPlant-v1.jar"]
EXPOSE 8080
