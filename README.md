# 微博分布式内容发布与互动平台

基于SpringCloud微服务架构的高性能内容平台，支持海量用户并发进行文章发布、实时互动与个性化推荐。

## 项目架构

### 后端微服务架构
- **gateway**: API网关，负责路由、限流及用户认证
- **user-service**: 用户微服务，负责用户注册、登录、信息管理
- **content-service**: 内容微服务，负责文章发布、管理
- **interaction-service**: 互动微服务，负责点赞、评论、收藏等互动功能
- **recommendation-service**: 推荐微服务，负责个性化推荐
- **common**: 公共模块，包含工具类、实体类、配置等

### 技术栈
- **后端**: SpringBoot + SpringCloud + MyBatis Plus + Redis + Kafka + Elasticsearch
- **前端**: Vue3 + ElementPlus + Vite + Pinia + Vue Router
- **数据库**: MySQL
- **缓存**: Redis
- **消息队列**: Kafka
- **搜索引擎**: Elasticsearch

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+
- Node.js 16+
- MySQL 8.0+
- Redis 6.0+
- Kafka 3.0+
- Elasticsearch 8.0+

### 1. 克隆项目
```bash
git clone <repository-url>
cd weiboDemo
```

### 2. 初始化数据库
创建数据库并执行SQL脚本（位置待定）。

### 3. 启动后端服务
```bash
cd backend

# 安装依赖
mvn clean install

# 启动各个微服务（按顺序）
mvn spring-boot:run -pl user-service
mvn spring-boot:run -pl content-service
mvn spring-boot:run -pl interaction-service
mvn spring-boot:run -pl recommendation-service
mvn spring-boot:run -pl gateway
```

### 4. 启动前端服务
```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

### 5. 访问应用
- 前端: http://localhost:3000
- 后端API: http://localhost:8080
- 网关: http://localhost:8080

## 项目结构

### 后端结构
```
backend/
├── common/                 # 公共模块
├── gateway/                # API网关
├── user-service/           # 用户微服务
├── content-service/        # 内容微服务
├── interaction-service/    # 互动微服务
├── recommendation-service/ # 推荐微服务
└── pom.xml                 # 父POM
```

### 前端结构
```
frontend/
├── src/
│   ├── api/               # API接口
│   ├── assets/            # 静态资源
│   ├── components/        # 公共组件
│   ├── layouts/           # 布局组件
│   ├── router/            # 路由配置
│   ├── store/             # 状态管理
│   ├── utils/             # 工具函数
│   ├── views/             # 页面组件
│   ├── App.vue            # 根组件
│   └── main.js            # 入口文件
├── index.html             # HTML模板
├── package.json           # 项目配置
└── vite.config.js         # Vite配置
```

## 主要功能

### 用户模块
- 用户注册/登录
- 用户信息管理
- 用户关注/粉丝

### 内容模块
- 文章发布/编辑/删除
- 内容分类管理
- 内容搜索

### 互动模块
- 点赞/取消点赞
- 评论/回复
- 收藏/取消收藏
- 分享功能

### 推荐模块
- 个性化推荐
- 热门内容推荐
- 话题推荐

## 开发指南

### 代码规范
- 后端遵循阿里巴巴Java开发手册
- 前端使用ESLint + Prettier进行代码格式化

### 提交规范
- feat: 新功能
- fix: 修复bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 代码重构
- test: 测试相关
- chore: 构建过程或辅助工具的变动

## 部署

### Docker部署
```bash
# 构建镜像
docker-compose build

# 启动服务
docker-compose up -d
```

### 生产环境配置
1. 修改各微服务的配置文件
2. 配置Nacos注册中心
3. 配置数据库连接信息
4. 配置Redis、Kafka、Elasticsearch连接信息

## 监控与运维
- 集成Spring Boot Actuator进行健康检查
- 使用Prometheus + Grafana进行监控
- 使用ELK进行日志收集