package com.weibo.recommendation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 推荐记录实体
 */
@Data
@TableName("recommendation")
public class Recommendation implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long targetId;

    private Integer targetType; // 1-内容, 2-用户

    private Integer algorithmType; // 推荐算法类型

    private Double score; // 推荐分数

    private Integer status; // 状态：1-有效，0-无效

    private LocalDateTime createTime;

    private LocalDateTime expireTime;
}