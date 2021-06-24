FROM openjdk:8-jdk-alpine
ADD https://github.com/microsoft/ApplicationInsights-Java/releases/download/3.1.0/applicationinsights-agent-3.1.0.jar /
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
