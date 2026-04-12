package com.weibo.interaction.service;

import com.weibo.interaction.entity.Interaction;

import java.util.List;
import java.util.Map;

/**
 * 互动服务接口
 */
public interface InteractionService {
    
    /**
     * 点赞
     */
    void like(Long userId, Long targetId, Integer targetType);
    
    /**
     * 取消点赞
     */
    void unlike(Long userId, Long targetId, Integer targetType);
    
    /**
     * 评论
     */
    void comment(Long userId, Long targetId, Integer targetType, String content);
    
    /**
     * 收藏
     */
    void collect(Long userId, Long targetId, Integer targetType);
    
    /**
     * 获取互动统计
     */
    Map<String, Integer> getStats(Long targetId, Integer targetType);
    
    /**
     * 获取用户的互动记录
     */
    List<Interaction> getUserInteractions(Long userId, Integer interactionType, Integer page, Integer size);
}