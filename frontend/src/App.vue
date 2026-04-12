<template>
  <div id="app">
    <el-container>
      <el-header>
        <div class="header-content">
          <div class="logo">
            <h2>广智 - 分布式内容发布与互动平台</h2>
          </div>
          <div class="nav-menu">
            <el-menu
              :default-active="activeMenu"
              mode="horizontal"
              @select="handleSelect"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b"
            >
              <el-menu-item index="home">首页</el-menu-item>
              <el-menu-item index="content" :disabled="!isLoggedIn">内容</el-menu-item>
              <el-menu-item index="interaction" :disabled="!isLoggedIn">互动</el-menu-item>
              <el-menu-item index="recommendation" :disabled="!isLoggedIn">推荐</el-menu-item>
            </el-menu>
          </div>
          <div class="user-info">
            <template v-if="isLoggedIn">
              <el-dropdown @command="handleUserCommand">
                <span class="user-dropdown">
                  <el-avatar :size="32" :src="userAvatar">{{ userNickname }}</el-avatar>
                  <span class="username">{{ userNickname }}</span>
                </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </template>
            <template v-else>
              <el-button type="primary" @click="showLogin">登录</el-button>
              <el-button type="success" @click="showRegister">注册</el-button>
            </template>
          </div>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer>
        <div class="footer-content">
          <p>© 2025 广智分布式内容发布与互动平台 | 基于SpringCloud微服务架构</p>
          <p>技术栈：SpringCloud + Vue3 + ElementPlus + Redis + Kafka + Elasticsearch</p>
        </div>
      </el-footer>
    </el-container>

    <!-- 登录对话框 -->
    <el-dialog v-model="loginDialogVisible" title="用户登录" width="30%">
      <el-form :model="loginForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="loginDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleLogin">登录</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 注册对话框 -->
    <el-dialog v-model="registerDialogVisible" title="用户注册" width="30%">
      <el-form :model="registerForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="registerForm.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="registerDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleRegister">注册</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { userApi } from '@/api'

const router = useRouter()
const activeMenu = ref('home')
const loginDialogVisible = ref(false)
const registerDialogVisible = ref(false)

const token = ref(localStorage.getItem('token') || '')
const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))

const isLoggedIn = computed(() => !!token.value)
const userNickname = computed(() => userInfo.value?.nickname || userInfo.value?.username || '用户')
const userAvatar = computed(() => userInfo.value?.avatar || '')

const loginForm = reactive({
  username: '',
  password: ''
})

const registerForm = reactive({
  username: '',
  password: '',
  email: '',
  nickname: ''
})

const handleSelect = (key) => {
  if (key !== 'home' && !isLoggedIn.value) {
    ElMessage.warning('请先登录')
    showLogin()
    return
  }
  activeMenu.value = key
  router.push(`/${key}`)
}

const handleUserCommand = (command) => {
  if (command === 'logout') {
    token.value = ''
    userInfo.value = {}
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    ElMessage.success('已退出登录')
    router.push('/home')
  } else if (command === 'profile') {
    ElMessage.info('个人中心功能开发中')
  }
}

const showLogin = () => {
  loginDialogVisible.value = true
}

const showRegister = () => {
  registerDialogVisible.value = true
}

const handleLogin = async () => {
  try {
    const res = await userApi.login(loginForm)
    if (res.code === 0 || res.code === 200) {
      const tokenVal = res.data?.token || res.token
      const userVal = res.data?.user || res.data || {}
      token.value = tokenVal
      userInfo.value = userVal
      localStorage.setItem('token', tokenVal)
      localStorage.setItem('userInfo', JSON.stringify(userVal))
      ElMessage.success('登录成功')
      loginDialogVisible.value = false
      loginForm.username = ''
      loginForm.password = ''
    } else {
      ElMessage.error(res.message || '登录失败')
    }
  } catch (error) {
    ElMessage.error(error.message || '登录失败')
  }
}

const handleRegister = async () => {
  try {
    const res = await userApi.register(registerForm)
    if (res.code === 0 || res.code === 200) {
      ElMessage.success('注册成功，请登录')
      registerDialogVisible.value = false
      loginDialogVisible.value = true
      registerForm.username = ''
      registerForm.password = ''
      registerForm.email = ''
      registerForm.nickname = ''
    } else {
      ElMessage.error(res.message || '注册失败')
    }
  } catch (error) {
    ElMessage.error(error.message || '注册失败')
  }
}

onMounted(() => {
  if (router.currentRoute.value.path === '/') {
    router.push('/home')
  }
})
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.el-header {
  background-color: #545c64;
  padding: 0 20px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
}

.logo h2 {
  color: white;
  margin: 0;
}

.nav-menu {
  flex: 1;
  margin: 0 20px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-dropdown {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: white;
}

.username {
  margin-left: 8px;
}

.el-footer {
  background-color: #f5f5f5;
  padding: 20px;
}

.footer-content {
  text-align: center;
}

.footer-content p {
  margin: 5px 0;
  color: #666;
}
</style>
