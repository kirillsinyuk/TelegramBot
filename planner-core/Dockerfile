FROM openjdk:17

ARG JAR=/build/libs/*.jar
COPY $JAR /core.jar
EXPOSE 8080

CMD java -jar /core.jar
