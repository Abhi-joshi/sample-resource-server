FROM maven:3.9.3-eclipse-temurin-17-alpine AS build
COPY src /home/sample-resource-server/src
COPY pom.xml /home/sample-resource-server
RUN mvn -f /home/sample-resource-server/pom.xml clean package -Dmaven.test.skip

FROM amazoncorretto:17
MAINTAINER abhishek
COPY --from=build /home/sample-resource-server/target/sample-resource-server-0.0.1-SNAPSHOT.jar sample-resource-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/sample-resource-server-0.0.1-SNAPSHOT.jar"]