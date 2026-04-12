package com.weibo.recommendation.service;

import com.weibo.recommendation.entity.Recommendation;

import java.util.List;
import java.util.Map;

/**
 * 推荐服务接口
 */
public interface RecommendationService {
    
    /**
     * 获取个性化推荐
     */
    List<Map<String, Object>> getPersonalRecommendation(Long userId, Integer size);
    
    /**
     * 获取热门推荐
     */
    List<Map<String, Object>> getHotRecommendation(Integer size);
    
    /**
     * 获取话题推荐
     */
    List<Map<String, Object>> getTopicRecommendation(Integer size);
    
    /**
     * 获取推荐用户
     */
    List<Map<String, Object>> getRecommendUser(Integer size);
    
    /**
     * 保存推荐记录
     */
    void saveRecommendation(Recommendation recommendation);
    
    /**
     * 获取用户推荐记录
     */
    List<Recommendation> getUserRecommendations(Long userId, Integer page, Integer size);
}