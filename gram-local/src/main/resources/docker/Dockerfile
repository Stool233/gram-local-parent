FROM java:8
ADD gram-local-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--resource.model-root-path=/opt/gram-model"]
EXPOSE 8083