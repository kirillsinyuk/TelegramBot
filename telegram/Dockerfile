FROM --platform=linux/x86_64 openjdk:17-jdk-alpine3.14

ARG JAR=/build/libs/*.jar
COPY $JAR telegram-adapter.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "telegram-adapter.jar.jar"]
