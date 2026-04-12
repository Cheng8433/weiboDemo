#!/bin/bash

echo "启动微博分布式内容发布与互动平台"

# 启动用户微服务
echo "启动用户微服务..."
cd user-service
mvn spring-boot:run &
cd ..

# 启动内容微服务
echo "启动内容微服务..."
cd content-service
mvn spring-boot:run &
cd ..

# 启动互动微服务
echo "启动互动微服务..."
cd interaction-service
mvn spring-boot:run &
cd ..

# 启动推荐微服务
echo "启动推荐微服务..."
cd recommendation-service
mvn spring-boot:run &
cd ..

# 启动网关
echo "启动网关..."
cd gateway
mvn spring-boot:run &
cd ..

echo "所有服务已启动"
echo "前端服务请在frontend目录下执行: npm run dev"
echo "访问地址："
echo "前端: http://localhost:3000"
echo "网关: http://localhost:8080"