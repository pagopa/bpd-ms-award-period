FROM cstar-azurecr-dev/cstar-docker-base:latest
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
