# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências (cache de layers)
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copia o código-fonte e empacota
COPY src ./src
RUN mvn package -DskipTests -q

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Cria usuário não-root para segurança
RUN addgroup -S junara && adduser -S junara -G junara

# Cria diretório de uploads e dá permissão ao usuário
RUN mkdir -p /app/uploads && chown -R junara:junara /app

COPY --from=build /app/target/*.jar app.jar

USER junara

EXPOSE 8080

ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-Dspring.profiles.active=prod", \
  "-jar", "app.jar"]
