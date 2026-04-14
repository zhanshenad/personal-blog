<template>
  <div class="auth-page">
    <div class="auth-box">
      <h2>注册</h2>
      <el-form @submit.prevent="handleRegister">
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.nickname" placeholder="昵称（选填）" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.email" placeholder="邮箱（选填）" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%" @click="handleRegister" :loading="loading">注册</el-button>
        </el-form-item>
      </el-form>
      <div style="text-align:center">
        已有账号？<router-link to="/login">去登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../api/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = ref({ username: '', nickname: '', email: '', password: '' })
const loading = ref(false)

async function handleRegister() {
  if (!form.value.username || !form.value.password) {
    return ElMessage.warning('请输入用户名和密码')
  }
  loading.value = true
  try {
    await register(form.value)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } finally {
    loading.value = false
  }
}
</script>
