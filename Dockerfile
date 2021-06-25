FROM cstar-docker-base
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
