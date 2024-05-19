FROM openjdk:17-jdk-alpine
VOLUME /tmp
add security-0.0.1-SNAPSHOT.jar docker_boot.jar
RUN sh -c 'touch /docker_boot.jar'
ENTRYPOINT ["java", "-jar", "/docker_boot.jar"]

EXPOSE 8060