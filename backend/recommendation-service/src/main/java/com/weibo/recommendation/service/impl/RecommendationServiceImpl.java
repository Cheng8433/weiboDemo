package com.weibo.recommendation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weibo.recommendation.entity.Recommendation;
import com.weibo.recommendation.mapper.RecommendationMapper;
import com.weibo.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 推荐服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    
    private final RecommendationMapper recommendationMapper;
    
    @Override
    public List<Map<String, Object>> getPersonalRecommendation(Long userId, Integer size) {
        log.info("获取个性化推荐：userId={}, size={}", userId, size);
        
        // 模拟个性化推荐数据（实际项目中应该使用推荐算法）
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("title", "个性化推荐内容 " + (i + 1));
            item.put("summary", "这是根据您的兴趣推荐的内容摘要...");
            item.put("contentType", i % 3);
            item.put("viewCount", 1000 + i * 100);
            item.put("likeCount", 100 + i * 10);
            item.put("createTime", "2025-12-15 " + (10 + i) + ":00:00");
            list.add(item);
            
            // 保存推荐记录到数据库
            saveRecommendation(userId, Long.valueOf(i + 1), 1, 1, 0.8);
        }
        
        return list;
    }
    
    @Override
    public List<Map<String, Object>> getHotRecommendation(Integer size) {
        log.info("获取热门推荐：size={}", size);
        
        // 模拟热门推荐数据
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("title", "热门内容 " + (i + 1));
            item.put("summary", "这是当前热门的内容摘要...");
            item.put("viewCount", 10000 + i * 1000);
            item.put("likeCount", 1000 + i * 100);
            item.put("commentCount", 100 + i * 10);
            item.put("createTime", "2025-12-15 " + (10 + i) + ":00:00");
            list.add(item);
        }
        
        return list;
    }
    
    @Override
    public List<Map<String, Object>> getTopicRecommendation(Integer size) {
        log.info("获取话题推荐：size={}", size);
        
        // 模拟话题推荐数据
        List<Map<String, Object>> list = new ArrayList<>();
        String[] topics = {
                "#SpringCloud微服务#", "#Vue3实战#", "#Redis优化#",
                "#Kafka消息队列#", "#Elasticsearch搜索#", "#Docker容器#",
                "#微服务架构#", "#分布式系统#", "#高并发设计#", "#性能优化#"
        };
        
        for (int i = 0; i < Math.min(size, topics.length); i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("name", topics[i]);
            item.put("count", 10000 - i * 1000);
            item.put("isHot", i < 3);
            list.add(item);
        }
        
        return list;
    }
    
    @Override
    public List<Map<String, Object>> getRecommendUser(Integer size) {
        log.info("获取推荐用户：size={}", size);
        
        // 模拟推荐用户数据
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", i + 1);
            item.put("nickname", "推荐用户" + (i + 1));
            item.put("avatar", "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
            item.put("bio", "这是推荐用户的简介...");
            item.put("followerCount", 1000 - i * 100);
            list.add(item);
        }
        
        return list;
    }
    
    @Override
    public void saveRecommendation(Recommendation recommendation) {
        recommendationMapper.insert(recommendation);
    }
    
    private void saveRecommendation(Long userId, Long targetId, Integer targetType, Integer algorithmType, Double score) {
        Recommendation recommendation = new Recommendation();
        recommendation.setUserId(userId);
        recommendation.setTargetId(targetId);
        recommendation.setTargetType(targetType);
        recommendation.setAlgorithmType(algorithmType);
        recommendation.setScore(score);
        recommendation.setStatus(1);
        recommendation.setCreateTime(LocalDateTime.now());
        recommendation.setExpireTime(LocalDateTime.now().plusDays(7));
        
        recommendationMapper.insert(recommendation);
    }
    
    @Override
    public List<Recommendation> getUserRecommendations(Long userId, Integer page, Integer size) {
        LambdaQueryWrapper<Recommendation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recommendation::getUserId, userId)
                .eq(Recommendation::getStatus, 1)
                .orderByDesc(Recommendation::getCreateTime);
        
        // 分页查询
        int offset = (page - 1) * size;
        queryWrapper.last("LIMIT " + offset + ", " + size);
        
        return recommendationMapper.selectList(queryWrapper);
    }
}