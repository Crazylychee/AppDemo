# 使用官方的 OpenJDK 8 镜像作为基础镜像
FROM openjdk:8-jre

# 设置工作目录
WORKDIR /app

# 将打包好的 Spring Boot JAR 文件复制到镜像中
COPY target/TestSystem-0.0.1-SNAPSHOT.jar /app/TestSystem-0.0.1-SNAPSHOT.jar

# 暴露应用程序的端口
EXPOSE 8080

# 启动应用程序
CMD ["java", "-jar", "TestSystem-0.0.1-SNAPSHOT.jar"]
