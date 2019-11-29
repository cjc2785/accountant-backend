FROM centos:7 AS build
RUN yum install maven -y 
WORKDIR /home/accountant
COPY . .
RUN mvn install



FROM openjdk:8-jre-alpine
WORKDIR /home/accountant
COPY --from=build /home/accountant/target/accountant-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar