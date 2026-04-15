<template>
  <div class="comment-section">
    <h3 style="margin-bottom:16px">评论 ({{ comments.length }})</h3>

    <div v-if="userStore.token" style="margin-bottom:20px">
      <el-input v-model="newComment" type="textarea" :rows="3" placeholder="写下你的评论..." />
      <el-button type="primary" style="margin-top:8px" @click="submitComment">发表评论</el-button>
    </div>
    <div v-else style="margin-bottom:16px;color:#999">
      <router-link to="/login">登录</router-link> 后可以评论
    </div>

    <div v-for="comment in comments" :key="comment.id" class="comment-item">
      <div class="header">
        <el-avatar :size="28" :src="comment.avatar || undefined">
          {{ (comment.username || '?')[0] }}
        </el-avatar>
        <span class="username">{{ comment.username }}</span>
        <span class="time">{{ formatDate(comment.createdAt) }}</span>
      </div>
      <div class="content">{{ comment.content }}</div>
    </div>
    <div v-if="!comments.length" style="color:#999;text-align:center;padding:20px">
      暂无评论，来抢沙发吧~
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getComments, addComment } from '../api/comment'
import { useUserStore } from '../stores/user'
import { ElMessage } from 'element-plus'

const props = defineProps({ articleId: { type: [Number, String], required: true } })
const userStore = useUserStore()
const comments = ref([])
const newComment = ref('')

onMounted(() => loadComments())

async function loadComments() {
  const res = await getComments(props.articleId)
  comments.value = res.data
}

async function submitComment() {
  if (!newComment.value.trim()) {
    return ElMessage.warning('请输入评论内容')
  }
  await addComment(props.articleId, { content: newComment.value })
  ElMessage.success('评论成功')
  newComment.value = ''
  loadComments()
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleString('zh-CN')
}
</script>
