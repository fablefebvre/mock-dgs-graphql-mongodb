FROM gradle:8.13.0-jdk21-alpine@sha256:3dd41aff1bf0421db1a094dbedd79ea18a55ec47e4b26f5d73625c7d0c88aa17 AS builder
WORKDIR /build
COPY . .
RUN gradle clean build

FROM bellsoft/liberica-runtime-container:jre-21-slim-musl@sha256:a22da01c44b626dd2e18cafb45558a6c73716fed18b45080122d9b88bb36c2c6 AS layers
WORKDIR /layer
COPY --from=builder /build/build/libs/mock-dgs-graphql-mongodb-0.0.1-SNAPSHOT.jar ./
RUN java -Djarmode=layertools -jar mock-dgs-graphql-mongodb-0.0.1-SNAPSHOT.jar extract

FROM bellsoft/liberica-runtime-container:jre-21-slim-musl@sha256:a22da01c44b626dd2e18cafb45558a6c73716fed18b45080122d9b88bb36c2c6
WORKDIR /opt/app
RUN addgroup --system appgroup && adduser -S -s /usr/sbin/nologin -G appgroup appuser
COPY --from=layers /layer/dependencies/ ./
COPY --from=layers /layer/spring-boot-loader/ ./
COPY --from=layers /layer/snapshot-dependencies/ ./
COPY --from=layers /layer/application/ ./
RUN chown -R appuser:appgroup /opt/app
USER appuser
HEALTHCHECK --interval=30s --timeout=3s --retries=1 CMD wget -qO- http://localhost:8080/actuator/health/ | grep UP || exit 1
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]