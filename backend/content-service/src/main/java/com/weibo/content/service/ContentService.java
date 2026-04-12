package com.weibo.content.service;

import com.weibo.content.dto.ContentDTO;
import com.weibo.content.vo.ContentVO;

import java.util.List;

public interface ContentService {
    ContentVO publishContent(ContentDTO contentDTO);
    ContentVO getContentById(Long id);
    ContentVO updateContent(Long id, ContentDTO contentDTO);
    void deleteContent(Long id);
    List<ContentVO> getContentList(Integer page, Integer size, Integer contentType);
    List<ContentVO> getUserContentList(Long userId, Integer page, Integer size);
    void incrementViewCount(Long id);
}