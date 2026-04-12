<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>最新内容</span>
              <el-button type="primary" size="small" @click="showPublishDialog = true">发布内容</el-button>
            </div>
          </template>
          <div v-loading="loading" class="content-list">
            <div v-for="item in contentList" :key="item.id" class="content-item">
              <div class="user-avatar">
                <el-avatar :size="40" :src="item.avatar || defaultAvatar"></el-avatar>
              </div>
              <div class="content-detail">
                <div class="user-info">
                  <span class="username">{{ item.username }}</span>
                  <span class="post-time">{{ formatTime(item.createTime) }}</span>
                </div>
                <div class="content-text">{{ item.content }}</div>
                <div v-if="item.images && item.images.length" class="content-images">
                  <el-image
                    v-for="(img, index) in item.images"
                    :key="index"
                    :src="img"
                    :preview-src-list="item.images"
                    fit="cover"
                    class="image-item"
                  />
                </div>
                <div class="content-actions">
                  <el-button type="text" @click="handleLike(item)">
                    <el-icon><Star /></el-icon> {{ item.likeCount || 0 }}
                  </el-button>
                  <el-button type="text" @click="handleComment(item)">
                    <el-icon><ChatDotRound /></el-icon> {{ item.commentCount || 0 }}
                  </el-button>
                  <el-button type="text" @click="handleShare(item)">
                    <el-icon><Share /></el-icon> {{ item.shareCount || 0 }}
                  </el-button>
                </div>
              </div>
            </div>
            <el-empty v-if="!loading && contentList.length === 0" description="暂无内容"></el-empty>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="ranking-card">
          <template #header>
            <div class="card-header">
              <span>热门排行榜</span>
            </div>
          </template>
          <div v-loading="rankingLoading" class="ranking-list">
            <div v-for="(item, index) in rankingList" :key="item.id" class="ranking-item">
              <div class="ranking-index">{{ index + 1 }}</div>
              <div class="ranking-info">
                <div class="ranking-title">{{ item.title }}</div>
                <div class="ranking-meta">
                  <span>{{ item.viewCount }} 阅读</span>
                  <span>{{ item.likeCount }} 点赞</span>
                </div>
              </div>
            </div>
            <el-empty v-if="!rankingLoading && rankingList.length === 0" description="暂无数据"></el-empty>
          </div>
        </el-card>
        <el-card class="recommend-card">
          <template #header>
            <div class="card-header">
              <span>推荐关注</span>
            </div>
          </template>
          <div v-loading="recommendLoading" class="recommend-list">
            <div v-for="item in recommendList" :key="item.id" class="recommend-item">
              <el-avatar :size="36" :src="item.avatar || defaultAvatar"></el-avatar>
              <div class="recommend-info">
                <div class="recommend-name">{{ item.nickname }}</div>
                <div class="recommend-bio">{{ item.bio }}</div>
              </div>
              <el-button type="primary" size="small" @click="handleFollow(item)">关注</el-button>
            </div>
            <el-empty v-if="!recommendLoading && recommendList.length === 0" description="暂无推荐"></el-empty>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 发布内容对话框 -->
    <el-dialog v-model="showPublishDialog" title="发布内容" width="50%">
      <el-form :model="publishForm" label-width="80px">
        <el-form-item label="内容">
          <el-input
            v-model="publishForm.content"
            type="textarea"
            :rows="4"
            placeholder="分享你的想法..."
            maxlength="500"
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showPublishDialog = false">取消</el-button>
          <el-button type="primary" @click="handlePublish" :loading="publishing">发布</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Star, ChatDotRound, Share } from '@element-plus/icons-vue'
import { contentApi, recommendationApi, userApi } from '@/api'
import dayjs from 'dayjs'
import 'dayjs/locale/zh-cn'

const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'

const loading = ref(false)
const rankingLoading = ref(false)
const recommendLoading = ref(false)
const showPublishDialog = ref(false)
const publishing = ref(false)

const contentList = ref([])
const rankingList = ref([])
const recommendList = ref([])

const publishForm = reactive({
  content: ''
})

const formatTime = (time) => {
  if (!time) return ''
  return dayjs(time).format('YYYY-MM-DD HH:mm:ss')
}

const fetchContentList = async () => {
  loading.value = true
  try {
    const res = await contentApi.getContentList({ page: 1, size: 20 })
    contentList.value = res.data || res || []
  } catch (error) {
    console.error('获取内容列表失败：', error)
    ElMessage.error('获取内容列表失败')
  } finally {
    loading.value = false
  }
}

const fetchHotList = async () => {
  rankingLoading.value = true
  try {
    const res = await recommendationApi.getHotList({ page: 1, size: 5 })
    rankingList.value = res.data || res || []
  } catch (error) {
    console.error('获取热门列表失败：', error)
  } finally {
    rankingLoading.value = false
  }
}

const fetchRecommendList = async () => {
  recommendLoading.value = true
  try {
    const res = await recommendationApi.getRecommendList({ page: 1, size: 5 })
    recommendList.value = res.data || res || []
  } catch (error) {
    console.error('获取推荐列表失败：', error)
  } finally {
    recommendLoading.value = false
  }
}

const handleLike = async (item) => {
  try {
    await contentApi.like({ contentId: item.id })
    item.likeCount = (item.likeCount || 0) + 1
    ElMessage.success('点赞成功')
  } catch (error) {
    console.error('点赞失败：', error)
  }
}

const handleComment = (item) => {
  console.log('评论：', item)
}

const handleShare = async (item) => {
  try {
    await contentApi.share({ contentId: item.id })
    item.shareCount = (item.shareCount || 0) + 1
    ElMessage.success('分享成功')
  } catch (error) {
    console.error('分享失败：', error)
  }
}

const handleFollow = async (item) => {
  try {
    await userApi.follow(item.id)
    ElMessage.success('关注成功')
  } catch (error) {
    console.error('关注失败：', error)
  }
}

const handlePublish = async () => {
  if (!publishForm.content.trim()) {
    ElMessage.warning('请输入内容')
    return
  }
  publishing.value = true
  try {
    await contentApi.createContent({ content: publishForm.content })
    ElMessage.success('发布成功')
    showPublishDialog.value = false
    publishForm.content = ''
    fetchContentList()
  } catch (error) {
    console.error('发布失败：', error)
    ElMessage.error('发布失败')
  } finally {
    publishing.value = false
  }
}

onMounted(() => {
  fetchContentList()
  fetchHotList()
  fetchRecommendList()
})
</script>

<style scoped>
.home {
  padding: 20px;
}

.content-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.content-list {
  min-height: 200px;
}

.content-item {
  display: flex;
  gap: 15px;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.content-item:last-child {
  border-bottom: none;
}

.content-detail {
  flex: 1;
}

.user-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.username {
  font-weight: bold;
  color: #409eff;
}

.post-time {
  color: #999;
  font-size: 12px;
}

.content-text {
  margin-bottom: 10px;
  line-height: 1.5;
}

.content-images {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.image-item {
  width: 100px;
  height: 100px;
  border-radius: 4px;
}

.content-actions {
  display: flex;
  gap: 20px;
}

.ranking-card {
  margin-bottom: 20px;
}

.ranking-list {
  min-height: 150px;
}

.ranking-item {
  display: flex;
  gap: 10px;
  align-items: center;
}

.ranking-index {
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

.ranking-item:nth-child(1) .ranking-index {
  background-color: #ff6b6b;
  color: white;
}

.ranking-item:nth-child(2) .ranking-index {
  background-color: #ffa726;
  color: white;
}

.ranking-item:nth-child(3) .ranking-index {
  background-color: #66bb6a;
  color: white;
}

.ranking-info {
  flex: 1;
}

.ranking-title {
  font-weight: 500;
  margin-bottom: 5px;
}

.ranking-meta {
  font-size: 12px;
  color: #999;
  display: flex;
  gap: 10px;
}

.recommend-list {
  min-height: 150px;
}

.recommend-item {
  display: flex;
  gap: 10px;
  align-items: center;
}

.recommend-info {
  flex: 1;
}

.recommend-name {
  font-weight: 500;
  margin-bottom: 3px;
}

.recommend-bio {
  font-size: 12px;
  color: #999;
}
</style>