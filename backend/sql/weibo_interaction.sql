-- 广智分布式内容发布与互动平台 - 互动数据库初始化脚本
-- Database: weibo_interaction

CREATE DATABASE IF NOT EXISTS weibo_interaction DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE weibo_interaction;

-- 互动表（点赞、评论、收藏）
DROP TABLE IF EXISTS `interaction`;
CREATE TABLE `interaction` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '互动ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `content_id` BIGINT(20) NOT NULL COMMENT '内容ID',
    `type` TINYINT(1) NOT NULL COMMENT '互动类型：1-点赞，2-评论，3-收藏',
    `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父ID（用于回复）',
    `content` TEXT DEFAULT NULL COMMENT '互动内容（评论/回复）',
    `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-删除，1-有效',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '是否删除：0-否，1-是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_content_id` (`content_id`),
    KEY `idx_type` (`type`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='互动表';

-- 通知表
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '接收用户ID',
    `from_user_id` BIGINT(20) NOT NULL COMMENT '发送用户ID',
    `content_id` BIGINT(20) DEFAULT NULL COMMENT '内容ID',
    `type` TINYINT(1) NOT NULL COMMENT '通知类型：1-点赞，2-评论，3-关注，4-提及',
    `content` VARCHAR(500) DEFAULT NULL COMMENT '通知内容',
    `status` TINYINT(1) DEFAULT 0 COMMENT '状态：0-未读，1-已读',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '是否删除：0-否，1-是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知表';

-- 插入互动测试数据
INSERT INTO `interaction` (`user_id`, `content_id`, `type`, `content`) VALUES
(1, 1, 1, NULL),
(2, 1, 1, NULL),
(3, 1, 1, NULL),
(4, 1, 1, NULL),
(5, 1, 1, NULL),
(1, 2, 1, NULL),
(3, 2, 1, NULL),
(4, 2, 1, NULL),
(1, 3, 1, NULL),
(2, 3, 1, NULL),
(1, 4, 1, NULL),
(5, 4, 1, NULL),
(2, 5, 1, NULL),
(3, 5, 1, NULL),
(1, 6, 1, NULL),
(2, 1, 2, '非常实用的文章，学习了！'),
(3, 1, 2, '感谢分享，讲解得很详细'),
(4, 2, 2, '组合式API确实很好用，已收藏'),
(1, 3, 2, '支持一下！'),
(2, 4, 2, 'Kafka入门的好文章'),
(1, 1, 3, NULL),
(2, 2, 3, NULL);

-- 插入通知测试数据
INSERT INTO `notification` (`user_id`, `from_user_id`, `content_id`, `type`, `content`, `status`) VALUES
(1, 2, 1, 1, '张三赞了你的内容', 0),
(1, 3, 1, 1, '李四赞了你的内容', 0),
(1, 3, 1, 2, '李四评论了你的内容', 0),
(1, 4, 1, 3, '王五关注了你', 0),
(1, 5, 1, 3, '赵六关注了你', 0),
(2, 1, 2, 3, '系统管理员关注了你', 1),
(3, 1, 3, 3, '系统管理员关注了你', 1),
(4, 1, 4, 3, '系统管理员关注了你', 0),
(2, 3, 1, 2, '李四评论了你的内容', 0),
(3, 2, 2, 1, '张三赞了你的内容', 0);