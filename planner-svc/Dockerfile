FROM eclipse-temurin:17-jre-alpine
EXPOSE 8443
ENTRYPOINT "/entrypoint.sh"
COPY build/libs/planner-svc-0.0.1-SNAPSHOT.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod a+x entrypoint.sh
