FROM openjdk:8
EXPOSE 8080
CMD ["java","-jar","app.jar"]
COPY build/libs/planner-1.0.jar app.jar