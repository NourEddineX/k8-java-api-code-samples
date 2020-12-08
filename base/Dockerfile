FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/api-0.0.1-SNAPSHOT.jar.jar
COPY ${JAR_FILE} api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/api-0.0.1-SNAPSHOT.jar"]