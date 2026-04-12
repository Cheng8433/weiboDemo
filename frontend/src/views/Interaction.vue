<template>
  <div class="interaction-page">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>互动动态</span>
            </div>
          </template>
          <div class="interaction-list">
            <div v-for="item in interactionList" :key="item.id" class="interaction-item">
              <div class="user-avatar">
                <el-avatar :size="40" :src="item.avatar"></el-avatar>
              </div>
              <div class="interaction-detail">
                <div class="user-info">
                  <span class="username">{{ item.username }}</span>
                  <span class="action-type">{{ item.actionType }}</span>
                  <span class="target-user">{{ item.targetUser }}</span>
                  <span class="post-time">{{ item.createTime }}</span>
                </div>
                <div class="interaction-content">{{ item.content }}</div>
                <div v-if="item.originalContent" class="original-content">
                  <el-card shadow="never" class="original-card">
                    <div class="original-user">{{ item.originalUser }}</div>
                    <div class="original-text">{{ item.originalContent }}</div>
                  </el-card>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>互动统计</span>
            </div>
          </template>
          <div class="stats-list">
            <div class="stat-item">
              <div class="stat-icon">
                <el-icon :size="24" color="#409eff"><Star /></el-icon>
              </div>
              <div class="stat-info">
                <div class="stat-value">1,234</div>
                <div class="stat-label">收到的点赞</div>
              </div>
            </div>
            <div class="stat-item">
              <div class="stat-icon">
                <el-icon :size="24" color="#67c23a"><ChatDotRound /></el-icon>
              </div>
              <div class="stat-info">
                <div class="stat-value">567</div>
                <div class="stat-label">收到的评论</div>
              </div>
            </div>
            <div class="stat-item">
              <div class="stat-icon">
                <el-icon :size="24" color="#e6a23c"><Share /></el-icon>
              </div>
              <div class="stat-info">
                <div class="stat-value">89</div>
                <div class="stat-label">收到的转发</div>
              </div>
            </div>
          </div>
        </el-card>
        <el-card>
          <template #header>
            <div class="card-header">
              <span>消息通知</span>
            </div>
          </template>
          <div class="notification-list">
            <div v-for="item in notifications" :key="item.id" class="notification-item">
              <div class="notification-icon">
                <el-icon :size="16" :color="item.color"><component :is="item.icon" /></el-icon>
              </div>
              <div class="notification-content">
                <div class="notification-text">{{ item.text }}</div>
                <div class="notification-time">{{ item.time }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Star, ChatDotRound, Share } from '@element-plus/icons-vue'
import { interactionApi } from '@/api'
import { ElMessage } from 'element-plus'

const loading = ref(false)

const fetchInteractionList = async () => {
  loading.value = true
  try {
    const res = await interactionApi.getList()
    interactionList.value = res.data || res || []
  } catch (error) {
    console.error('获取互动列表失败：', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchInteractionList()
})

const interactionList = ref([])
const notifications = ref([])

const getActionIcon = (type) => {
  switch (type) {
    case '赞了': return Star
    case '评论了': return ChatDotRound
    case '转发了': return Share
    default: return Star
  }
}
</script>

<style scoped>
.interaction-page {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.interaction-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.interaction-item {
  display: flex;
  gap: 15px;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.interaction-item:last-child {
  border-bottom: none;
}

.interaction-detail {
  flex: 1;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}

.username {
  font-weight: bold;
  color: #409eff;
}

.action-type {
  color: #666;
}

.target-user {
  color: #409eff;
}

.post-time {
  color: #999;
  font-size: 12px;
  margin-left: auto;
}

.interaction-content {
  margin-bottom: 8px;
  line-height: 1.5;
}

.original-content {
  margin-top: 10px;
}

.original-card {
  background-color: #f5f7fa;
}

.original-user {
  font-weight: 500;
  margin-bottom: 5px;
  color: #409eff;
}

.original-text {
  color: #666;
  line-height: 1.5;
}

.stats-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.stat-item {
  display: flex;
  gap: 15px;
  align-items: center;
  padding: 10px;
  border-radius: 4px;
  background-color: #f5f7fa;
}

.stat-icon {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  border-radius: 4px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  font-size: 12px;
  color: #909399;
}

.notification-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.notification-item {
  display: flex;
  gap: 10px;
  align-items: center;
}

.notification-icon {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.notification-content {
  flex: 1;
}

.notification-text {
  font-size: 14px;
  color: #303133;
  margin-bottom: 3px;
}

.notification-time {
  font-size: 12px;
  color: #909399;
}
</style>