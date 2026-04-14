<template>
  <div>
    <h2 style="margin-bottom:16px">评论管理</h2>

    <el-table :data="comments" border style="width:100%">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="username" label="用户" width="100" />
      <el-table-column prop="articleTitle" label="文章" min-width="200" />
      <el-table-column prop="content" label="内容" min-width="300" />
      <el-table-column label="时间" width="170">
        <template #default="{ row }">{{ formatDate(row.createdAt) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAdminComments, deleteComment } from '../../api/comment'
import { ElMessage, ElMessageBox } from 'element-plus'

const comments = ref([])

onMounted(() => loadComments())

async function loadComments() {
  const res = await getAdminComments()
  comments.value = res.data
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除该评论？', '提示', { type: 'warning' })
  await deleteComment(id)
  ElMessage.success('删除成功')
  loadComments()
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleString('zh-CN')
}
</script>
