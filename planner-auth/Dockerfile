FROM openjdk:11-jre-slim
EXPOSE 8442
ENTRYPOINT "/entrypoint.sh"
COPY build/libs/planner-auth-1.0.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod a+x entrypoint.sh
