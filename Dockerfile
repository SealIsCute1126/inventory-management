# 第一階段：編譯階段
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 複製設定並下載依賴
COPY pom.xml .
RUN mvn dependency:go-offline

# 複製原始碼並打包
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# 第二階段：運行階段
FROM eclipse-temurin:21-jre
WORKDIR /app

# 從第一階段複製打包好的 jar 檔
COPY --from=build /app/target/*.jar app.jar

# 啟動程式
ENTRYPOINT ["java", "-jar", "app.jar"]