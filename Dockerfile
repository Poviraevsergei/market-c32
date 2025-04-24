FROM openjdk:21-jdk
ARG JAR_FILE=target/market-c32-1.0-SNAPSHOT.jar
RUN mkdir /c32
WORKDIR /c32
COPY ${JAR_FILE} /c32
ENTRYPOINT ["java", "-jar", "market-c32-1.0-SNAPSHOT.jar"]