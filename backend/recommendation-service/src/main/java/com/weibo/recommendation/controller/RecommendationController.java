package com.weibo.recommendation.controller;

import com.weibo.recommendation.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "推荐管理", description = "推荐相关接口")
@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    
    private final RecommendationService recommendationService;
    
    @Operation(summary = "获取个性化推荐")
    @GetMapping("/personal")
    public Map<String, Object> getPersonalRecommendation(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> data = recommendationService.getPersonalRecommendation(userId, size);
            result.put("success", true);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @Operation(summary = "获取热门推荐")
    @GetMapping("/hot")
    public Map<String, Object> getHotRecommendation(
            @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> data = recommendationService.getHotRecommendation(size);
            result.put("success", true);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @Operation(summary = "获取话题推荐")
    @GetMapping("/topic")
    public Map<String, Object> getTopicRecommendation(
            @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> data = recommendationService.getTopicRecommendation(size);
            result.put("success", true);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @Operation(summary = "获取推荐用户")
    @GetMapping("/user")
    public Map<String, Object> getRecommendUser(
            @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> data = recommendationService.getRecommendUser(size);
            result.put("success", true);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}