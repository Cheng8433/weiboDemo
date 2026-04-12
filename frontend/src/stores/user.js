import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('token') || '')
  const isLoggedIn = ref(!!token.value)

  function setUser(newUser) {
    user.value = newUser
    isLoggedIn.value = !!newUser
  }

  function setToken(newToken) {
    token.value = newToken
    localStorage.setItem('token', newToken)
    isLoggedIn.value = !!newToken
  }

  function logout() {
    user.value = null
    token.value = ''
    isLoggedIn.value = false
    localStorage.removeItem('token')
  }

  return {
    user,
    token,
    isLoggedIn,
    setUser,
    setToken,
    logout
  }
})