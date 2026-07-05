FROM maven:3.9.9-eclipse-temurin-21 AS buildstage

WORKDIR /app

COPY pom.xml ./
COPY src/ src/
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre

WORKDIR /app

ENV DB_WALLET_PATH=/opt/oracle/wallet

RUN mkdir -p /opt/oracle/wallet

COPY --from=buildstage /app/target/plataformaEducativa-1.2.0.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
