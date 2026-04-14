<template>
  <nav class="navbar">
    <div class="navbar-brand" @click="$router.push('/')">My Blog</div>
    <div class="navbar-links">
      <router-link to="/">首页</router-link>
      <template v-if="userStore.token">
        <router-link to="/admin/dashboard" v-if="userStore.isAdmin">管理后台</router-link>
        <span @click="handleLogout">退出</span>
      </template>
      <template v-else>
        <router-link to="/login">登录</router-link>
        <router-link to="/register">注册</router-link>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { useUserStore } from '../stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

function handleLogout() {
  userStore.logout()
  router.push('/')
}
</script>
