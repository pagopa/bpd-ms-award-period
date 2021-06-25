FROM cstardacr.azurecr.io/cstar-docker-base:jdk11-ai3.1.0
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
