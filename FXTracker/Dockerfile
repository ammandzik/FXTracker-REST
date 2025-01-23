FROM ubuntu:latest
LABEL authors="Amanda"
FROM openjdk:23
WORKDIR /FXTracker
COPY . /FXTracker

RUN ./mvn clean install -DskipTests

CMD ["java", "-jar", "target/FXTracker.jar"]

ENTRYPOINT ["top", "-b"]