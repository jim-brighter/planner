FROM openjdk:8-jre-alpine
EXPOSE 8282
ENTRYPOINT "/entrypoint.sh"
COPY build/libs/planner-auth-1.0.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod a+x entrypoint.sh