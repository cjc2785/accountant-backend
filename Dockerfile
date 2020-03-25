FROM maven:3-jdk-8-slim AS build
WORKDIR /home/accountant
COPY . .
RUN mvn -q install



FROM openjdk:8-jre-alpine
WORKDIR /home/accountant
COPY --from=build /home/accountant/target/accountant-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar