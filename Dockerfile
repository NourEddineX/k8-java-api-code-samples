# Step : Test and package
FROM maven:3-jdk-11 AS target
WORKDIR /build
COPY pom.xml .
COPY base/base.iml base/base.iml
COPY base/pom.xml base/pom.xml
COPY library/library.iml library/library.iml
COPY library/pom.xml library/pom.xml
COPY glasswall-solutions.iml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn -Dmaven.test.skip=true install 

# Step : Package image
FROM openjdk:11-jre-buster
COPY --from=target /build/base/target/base-1.0-SNAPSHOT.jar /app/my-app.jar
ENV REBUILD_API_URL=http://sow-rest-api
CMD java \
    -Drebuild.api.url=${REBUILD_API_URL} \
    -jar /app/my-app.jar