import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))
  const isAdmin = ref(userInfo.value?.role === 'ROLE_ADMIN')

  function setLogin(tokenVal, user) {
    token.value = tokenVal
    userInfo.value = user
    isAdmin.value = user.role === 'ROLE_ADMIN'
    localStorage.setItem('token', tokenVal)
    localStorage.setItem('userInfo', JSON.stringify(user))
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    isAdmin.value = false
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  return { token, userInfo, isAdmin, setLogin, logout }
})
