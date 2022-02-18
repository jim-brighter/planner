FROM eclipse-temurin:17-jre-alpine
EXPOSE 8442
ENTRYPOINT "/entrypoint.sh"
COPY build/libs/planner-auth-0.0.1-SNAPSHOT.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod a+x entrypoint.sh
