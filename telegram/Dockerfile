FROM openjdk:17

ARG JAR=/build/libs/*.jar
COPY $JAR telegram-adapter.jar
EXPOSE 8080

CMD java -jar /core.jar
