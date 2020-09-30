FROM openjdk:14-jdk-alpine
ARG JAR_FILE=target/DatingGenerator-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]