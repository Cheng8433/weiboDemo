-- 广智分布式内容发布与互动平台 - 内容数据库初始化脚本
-- Database: weibo_content

CREATE DATABASE IF NOT EXISTS weibo_content DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE weibo_content;

-- 内容表
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '内容ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `title` VARCHAR(200) NOT NULL COMMENT '标题',
    `summary` VARCHAR(500) DEFAULT NULL COMMENT '摘要',
    `content` TEXT NOT NULL COMMENT '内容',
    `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图片URL',
    `images` JSON DEFAULT NULL COMMENT '图片URL列表（JSON格式）',
    `video_url` VARCHAR(500) DEFAULT NULL COMMENT '视频URL',
    `content_type` TINYINT(1) DEFAULT 1 COMMENT '内容类型：1-文章，2-图片，3-视频',
    `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-草稿，1-已发布，2-审核中，3-已下架',
    `view_count` INT(11) DEFAULT 0 COMMENT '浏览量',
    `like_count` INT(11) DEFAULT 0 COMMENT '点赞数',
    `comment_count` INT(11) DEFAULT 0 COMMENT '评论数',
    `share_count` INT(11) DEFAULT 0 COMMENT '分享数',
    `collect_count` INT(11) DEFAULT 0 COMMENT '收藏数',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '是否删除：0-否，1-是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_content_type` (`content_type`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_view_count` (`view_count`),
    KEY `idx_like_count` (`like_count`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容表';

-- 内容标签关联表
DROP TABLE IF EXISTS `content_tag`;
CREATE TABLE `content_tag` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `content_id` BIGINT(20) NOT NULL COMMENT '内容ID',
    `tag_id` BIGINT(20) NOT NULL COMMENT '标签ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_content_tag` (`content_id`, `tag_id`),
    KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容标签关联表';

-- 标签表
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    `name` VARCHAR(50) NOT NULL COMMENT '标签名称',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '标签描述',
    `content_count` INT(11) DEFAULT 0 COMMENT '内容数量',
    `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- 插入测试数据
INSERT INTO `content` (`user_id`, `title`, `summary`, `content`, `content_type`, `status`, `view_count`, `like_count`, `comment_count`, `share_count`) VALUES
(1, 'SpringCloud微服务架构详解', '深入浅出讲解SpringCloud微服务架构的核心概念与实践', 'SpringCloud是一系列框架的有序集合，利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发。通过Spring Cloud开发的应用程序很容易在分布式系统中进行构建，它们兼具简单、可维护和可测试性。\n\n微服务架构是一种���构风格，它将单个应用程序划分为一组小服务，服务之间互相协调、互相配合。每个服务运行在独立进程中，服务与服务之间用轻量级通信机制（HTTP）进行沟通。', 1, 1, 12580, 1024, 328, 156),
(2, 'Vue3组合式API实战', '从零开始学习Vue3的组合式API，提升代码组织能力', 'Vue3引入了组合式API，让我们可以更灵活地组织组件逻辑。通过setup函数，我们可以使用响应式api、计算属性、监听器等，让代码更加简洁和可复用。\n\n组合式API是Vue3最重要的特性之一，它解决了Vue2中逻辑复用困难的问题，让代码组织更加清晰。', 1, 1, 9876, 876, 245, 128),
(3, 'Redis缓存优化技巧', '分享Redis在实际项目中的性能优化经验', 'Redis作为高性能的键值数据库，在分布式系统中常用于缓存、会话存储、消息队列等场景。合理使用Redis可以大幅提升系统性能。\n\n本文分享一些在实际项目中的Redis优化技巧，包括缓存策略、内存优化、集群部署等方面。', 1, 1, 8654, 765, 189, 98),
(3, 'Kafka消息队列实战', '详解Kafka的安装配置与使用', 'Kafka是一种分布式流处理平台，可以实时处理大量数据消息。本文介绍Kafka的基本概念、安装配置和Java集成。\n\nKafka最初由LinkedIn开发，后成为Apache顶级项目。它具有高吞吐量、低延迟、可扩展等特点。', 1, 1, 7654, 654, 167, 87),
(2, 'Elasticsearch搜索引擎入门', 'ES从入门到精通完全指南', 'Elasticsearch是一个基于Lucene的搜索服务器，提供了分布式全文搜索功能。本文详细介绍ES的安装、配置和使用方法。\n\nElasticsearch常用于全文搜索、日志分析、业务搜索等场景，是现代分布式系统中不可或缺的组件。', 1, 1, 6543, 543, 145, 76),
(4, 'MySQL性能优化实战', '数据库性能优化必看', 'MySQL是最流行的关系型数据库之一，本文分享一些MySQL性能优化的实战经验，包括索引优化、查询优化、配置优化等方面。\n\n通过对SQL语句的执行计划分析，我们可以找到性能瓶颈并进行针对性优化。', 1, 1, 5432, 432, 123, 65);

-- 插入标签数据
INSERT INTO `tag` (`name`, `description`, `content_count`) VALUES
('SpringCloud', 'SpringCloud微服务框架', 1),
('Vue3', 'Vue3前端框架', 1),
('Redis', 'Redis缓存数据库', 1),
('微服务', '微服务架构', 1),
('性能优化', '系统性能优化', 1),
('Kafka', 'Kafka消息队列', 1),
('Elasticsearch', 'Elasticsearch搜索引擎', 1),
('MySQL', 'MySQL数据库', 1),
('Docker', 'Docker容器化', 1),
('SpringBoot', 'SpringBoot框架', 1),
('前端', '前端技术', 1),
('后端', '后端技术', 4);

-- 插入内容标签关联数据
INSERT INTO `content_tag` (`content_id`, `tag_id`) VALUES
(1, 1), (1, 4),
(2, 2), (2, 11),
(3, 3), (3, 5),
(4, 6),
(5, 7),
(6, 8), (6, 5);