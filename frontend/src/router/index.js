import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Content from '../views/Content.vue'
import Interaction from '../views/Interaction.vue'
import Recommendation from '../views/Recommendation.vue'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    meta: { title: '首页' }
  },
  {
    path: '/content',
    name: 'Content',
    component: Content,
    meta: { title: '内容' }
  },
  {
    path: '/interaction',
    name: 'Interaction',
    component: Interaction,
    meta: { title: '互动' }
  },
  {
    path: '/recommendation',
    name: 'Recommendation',
    component: Recommendation,
    meta: { title: '推荐' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router