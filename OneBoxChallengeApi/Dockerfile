FROM eclipse-temurin:17-jdk
WORKDIR /build
COPY mvnw .
COPY .mvn ./.mvn
COPY src ./src
COPY pom.xml .
RUN chmod +x mvnw
RUN chmod -R +rx .mvn
EXPOSE 8080
CMD ["sh", "-c", "./mvnw clean package && java -jar target/*.jar"]
