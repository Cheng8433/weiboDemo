# 构建阶段
FROM openjdk:17-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN chmod +x mvnw 2>/dev/null || true
# 如果项目有mvnw，使用mvnw，否则使用mvn
RUN if [ -f mvnw ]; then ./mvnw clean package -DskipTests; else mvn clean package -DskipTests; fi

# 运行阶段
FROM openjdk:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]