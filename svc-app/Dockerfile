FROM openjdk:8-jre-alpine
EXPOSE 8080
ENTRYPOINT "/entrypoint.sh"
COPY build/libs/planner-1.0.jar app.jar
COPY ./entrypoint.sh /entrypoint.sh