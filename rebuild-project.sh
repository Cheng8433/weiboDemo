#!/bin/bash

echo "清理Maven缓存并重新构建项目"

# 清理Maven本地缓存
echo "清理Maven本地缓存..."
rm -rf ~/.m2/repository/com/alibaba/cloud
rm -rf ~/.m2/repository/com/weibo
rm -rf ~/.m2/repository/org/springframework/cloud

# 清理项目构建文件
echo "清理项目构建文件..."
find . -name "target" -type d -exec rm -rf {} + 2>/dev/null || true

# 重新构建common模块
echo "构建common模块..."
cd backend/common
mvn clean install -DskipTests
cd ../..

# 重新构建其他模块
echo "构建user-service..."
cd backend/user-service
mvn clean compile -DskipTests
cd ../..

echo "构建content-service..."
cd backend/content-service
mvn clean compile -DskipTests
cd ../..

echo "构建interaction-service..."
cd backend/interaction-service
mvn clean compile -DskipTests
cd ../..

echo "构建recommendation-service..."
cd backend/recommendation-service
mvn clean compile -DskipTests
cd ../..

echo "构建gateway..."
cd backend/gateway
mvn clean compile -DskipTests
cd ../..

echo "所有模块构建完成"
echo "如果还有问题，请查看Maven依赖问题解决方案.md文件"