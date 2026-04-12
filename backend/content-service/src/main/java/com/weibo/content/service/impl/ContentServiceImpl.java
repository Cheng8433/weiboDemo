package com.weibo.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weibo.content.dto.ContentDTO;
import com.weibo.content.entity.Content;
import com.weibo.content.mapper.ContentMapper;
import com.weibo.content.service.ContentService;
import com.weibo.content.vo.ContentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    
    private final ContentMapper contentMapper;
    
    @Override
    public ContentVO publishContent(ContentDTO contentDTO) {
        log.info("发布内容：{}", contentDTO.getTitle());
        
        Content content = new Content();
        BeanUtils.copyProperties(contentDTO, content);
        content.setViewCount(0);
        content.setLikeCount(0);
        content.setCommentCount(0);
        content.setShareCount(0);
        content.setCollectCount(0);
        content.setStatus(1);
        
        contentMapper.insert(content);
        
        return convertToVO(content);
    }
    
    @Override
    public ContentVO getContentById(Long id) {
        Content content = contentMapper.selectById(id);
        if (content == null) {
            throw new RuntimeException("内容不存在");
        }
        return convertToVO(content);
    }
    
    @Override
    public ContentVO updateContent(Long id, ContentDTO contentDTO) {
        Content content = contentMapper.selectById(id);
        if (content == null) {
            throw new RuntimeException("内容不存在");
        }
        
        BeanUtils.copyProperties(contentDTO, content);
        
        contentMapper.updateById(content);
        
        return convertToVO(content);
    }
    
    @Override
    public void deleteContent(Long id) {
        contentMapper.deleteById(id);
    }
    
    @Override
    public List<ContentVO> getContentList(Integer page, Integer size, Integer contentType) {
        LambdaQueryWrapper<Content> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Content::getStatus, 0)
                .orderByDesc(Content::getCreateTime);
        
        if (contentType != null) {
            queryWrapper.eq(Content::getContentType, contentType);
        }
        
        // 分页查询
        int offset = (page - 1) * size;
        queryWrapper.last("LIMIT " + offset + ", " + size);
        
        List<Content> list = contentMapper.selectList(queryWrapper);
        
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }
    
    @Override
    public List<ContentVO> getUserContentList(Long userId, Integer page, Integer size) {
        LambdaQueryWrapper<Content> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Content::getUserId, userId)
                .orderByDesc(Content::getCreateTime);
        
        // 分页查询
        int offset = (page - 1) * size;
        queryWrapper.last("LIMIT " + offset + ", " + size);
        
        List<Content> list = contentMapper.selectList(queryWrapper);
        
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }
    
    @Override
    public void incrementViewCount(Long id) {
        Content content = contentMapper.selectById(id);
        if (content != null) {
            content.setViewCount(content.getViewCount() + 1);
            contentMapper.updateById(content);
        }
    }
    
    private ContentVO convertToVO(Content content) {
        ContentVO vo = new ContentVO();
        BeanUtils.copyProperties(content, vo);
        vo.setUsername("用户" + content.getUserId());
        vo.setUserAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        return vo;
    }
}