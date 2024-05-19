FROM openjdk:17-jdk-alpine
VOLUME /tmp
add edumindai.jar docker_boot.jar
RUN sh -c 'touch /docker_boot.jar'
ENTRYPOINT ["java", "-jar", "/docker_boot.jar"]

EXPOSE 8060