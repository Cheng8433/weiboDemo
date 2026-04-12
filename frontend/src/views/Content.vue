<template>
  <div class="content-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>内容管理</span>
          <el-button type="primary">发布新内容</el-button>
        </div>
      </template>
      <div class="content-search">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索内容"
          class="search-input"
          clearable
        >
          <template #append>
            <el-button :icon="Search">搜索</el-button>
          </template>
        </el-input>
        <el-select v-model="contentType" placeholder="内容类型" class="type-select">
          <el-option label="全部" value=""></el-option>
          <el-option label="文章" value="article"></el-option>
          <el-option label="图片" value="image"></el-option>
          <el-option label="视频" value="video"></el-option>
        </el-select>
      </div>
      <div class="content-list">
        <el-table :data="contentData" style="width: 100%">
          <el-table-column prop="title" label="标题" width="300"></el-table-column>
          <el-table-column prop="author" label="作者" width="120"></el-table-column>
          <el-table-column prop="type" label="类型" width="100">
            <template #default="scope">
              <el-tag :type="getTagType(scope.row.type)">{{ scope.row.type }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="viewCount" label="阅读量" width="100"></el-table-column>
          <el-table-column prop="likeCount" label="点赞数" width="100"></el-table-column>
          <el-table-column prop="createTime" label="发布时间" width="180"></el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" size="small">编辑</el-button>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination">
        <el-pagination
          :page-size="20"
          :pager-count="11"
          layout="total, prev, pager, next"
          :total="100"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { contentApi } from '@/api'

const searchKeyword = ref('')
const contentType = ref('')
const loading = ref(false)
const contentData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const fetchContentList = async () => {
  loading.value = true
  try {
    const res = await contentApi.getContentList({ 
      page: currentPage.value, 
      size: pageSize.value,
      keyword: searchKeyword.value,
      contentType: contentType.value 
    })
    contentData.value = res.data || res || []
    total.value = res.total || 0
  } catch (error) {
    console.error('获取内容列表失败：', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  currentPage.value = 1
  fetchContentList()
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchContentList()
}

onMounted(() => {
  fetchContentList()
})

const getTagType = (type) => {
  const types = {
    '文章': '',
    '图片': 'success',
    '视频': 'warning'
  }
  return types[type] || ''
}
</script>

<style scoped>
.content-page {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.content-search {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.search-input {
  flex: 1;
}

.type-select {
  width: 120px;
}

.content-list {
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
}
</style>