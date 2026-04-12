<template>
  <div class="recommendation-page">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>个性化推荐</span>
              <el-button type="primary" size="small">刷新推荐</el-button>
            </div>
          </template>
          <div class="recommend-list">
            <div v-for="item in recommendList" :key="item.id" class="recommend-item">
              <div class="recommend-header">
                <el-avatar :size="40" :src="item.avatar"></el-avatar>
                <div class="recommend-info">
                  <div class="recommend-title">{{ item.title }}</div>
                  <div class="recommend-meta">
                    <span class="recommend-author">{{ item.author }}</span>
                    <span class="recommend-time">{{ item.createTime }}</span>
                  </div>
                </div>
                <el-tag :type="getTagType(item.type)">{{ item.type }}</el-tag>
              </div>
              <div class="recommend-content">{{ item.content }}</div>
              <div v-if="item.images && item.images.length" class="recommend-images">
                <el-image
                  v-for="(img, index) in item.images"
                  :key="index"
                  :src="img"
                  :preview-src-list="item.images"
                  fit="cover"
                  class="image-item"
                />
              </div>
              <div class="recommend-stats">
                <span><el-icon><View /></el-icon> {{ item.viewCount }}</span>
                <span><el-icon><Star /></el-icon> {{ item.likeCount }}</span>
                <span><el-icon><ChatDotRound /></el-icon> {{ item.commentCount }}</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>热门话题</span>
            </div>
          </template>
          <div class="topic-list">
            <div v-for="item in topicList" :key="item.id" class="topic-item">
              <div class="topic-index">{{ item.index }}</div>
              <div class="topic-info">
                <div class="topic-name">{{ item.name }}</div>
                <div class="topic-count">{{ item.count }} 讨论</div>
              </div>
              <el-tag v-if="item.isHot" type="danger" size="small">热门</el-tag>
            </div>
          </div>
        </el-card>
        <el-card>
          <template #header>
            <div class="card-header">
              <span>推荐用户</span>
            </div>
          </template>
          <div class="user-list">
            <div v-for="item in userList" :key="item.id" class="user-item">
              <el-avatar :size="36" :src="item.avatar"></el-avatar>
              <div class="user-info">
                <div class="user-name">{{ item.name }}</div>
                <div class="user-desc">{{ item.desc }}</div>
              </div>
              <el-button type="primary" size="small">关注</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { View, Star, ChatDotRound } from '@element-plus/icons-vue'
import { recommendationApi } from '@/api'

const recommendList = ref([])
const loading = ref(false)
const topicList = ref([])
const userList = ref([])

const fetchRecommendList = async () => {
  loading.value = true
  try {
    const res = await recommendationApi.getRecommendList({ page: 1, size: 10 })
    recommendList.value = res.data || res || []
  } catch (error) {
    console.error('获取推荐列表失败：', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchRecommendList()
})

const getTagType = (type) => {
  const types = {
    '文章': '',
    '教程': 'success',
    '技术分享': 'warning',
    '视频': 'danger'
  }
  return types[type] || ''
}
</script>

<style scoped>
.recommendation-page {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.recommend-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.recommend-item {
  padding: 15px;
  border: 1px solid #eee;
  border-radius: 4px;
  transition: all 0.3s;
}

.recommend-item:hover {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.recommend-header {
  display: flex;
  gap: 15px;
  align-items: center;
  margin-bottom: 10px;
}

.recommend-info {
  flex: 1;
}

.recommend-title {
  font-weight: bold;
  margin-bottom: 5px;
  font-size: 16px;
}

.recommend-meta {
  display: flex;
  gap: 10px;
  font-size: 12px;
  color: #999;
}

.recommend-content {
  margin-bottom: 10px;
  line-height: 1.5;
  color: #666;
}

.recommend-images {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.image-item {
  width: 100px;
  height: 100px;
  border-radius: 4px;
}

.recommend-stats {
  display: flex;
  gap: 20px;
  color: #999;
  font-size: 12px;
}

.topic-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.topic-item {
  display: flex;
  gap: 10px;
  align-items: center;
}

.topic-index {
  width: 24px;
  height: 24px;
  background-color: #f0f0f0;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  color: #666;
}

.topic-item:nth-child(1) .topic-index {
  background-color: #ff6b6b;
  color: white;
}

.topic-item:nth-child(2) .topic-index {
  background-color: #ffa726;
  color: white;
}

.topic-item:nth-child(3) .topic-index {
  background-color: #66bb6a;
  color: white;
}

.topic-info {
  flex: 1;
}

.topic-name {
  font-weight: 500;
  margin-bottom: 3px;
}

.topic-count {
  font-size: 12px;
  color: #999;
}

.user-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.user-item {
  display: flex;
  gap: 10px;
  align-items: center;
}

.user-info {
  flex: 1;
}

.user-name {
  font-weight: 500;
  margin-bottom: 3px;
}

.user-desc {
  font-size: 12px;
  color: #999;
}
</style>