FROM openjdk:25

WORKDIR /server
COPY target/gharbazaar.jar .
EXPOSE 80

CMD ["java", "-jar", "gharbazaar.jar"]