FROM openjdk:8-jre-alpine
EXPOSE 8443
ENTRYPOINT "/entrypoint.sh"
COPY build/libs/planner-svc-1.0.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod a+x entrypoint.sh