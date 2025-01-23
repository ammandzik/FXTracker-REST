FROM ubuntu:latest
LABEL authors="Amanda"
FROM openjdk:23
WORKDIR /FXTracker
COPY . /FXTracker

CMD ["java", "-jar", "target/FXTracker.jar"]

ENTRYPOINT ["top", "-b"]