<template>
  <div>
    <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:16px">
      <h2>文章管理</h2>
      <el-button type="primary" @click="$router.push('/admin/articles/create')">写文章</el-button>
    </div>

    <el-table :data="articles" border style="width:100%">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column prop="categoryName" label="分类" width="100" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '已发布' : '草稿' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="viewCount" label="浏览" width="80" />
      <el-table-column label="创建时间" width="170">
        <template #default="{ row }">{{ formatDate(row.createdAt) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button size="small" @click="$router.push(`/admin/articles/edit/${row.id}`)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
        v-model:current-page="page"
        :page-size="10"
        :total="total"
        layout="prev, pager, next"
        @current-change="loadArticles"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAdminArticles, deleteArticle } from '../../api/article'
import { ElMessage, ElMessageBox } from 'element-plus'

const articles = ref([])
const page = ref(1)
const total = ref(0)

onMounted(() => loadArticles())

async function loadArticles() {
  const res = await getAdminArticles({ page: page.value, size: 10 })
  articles.value = res.data.records
  total.value = Number(res.data.total)
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除这篇文章？', '提示', { type: 'warning' })
  await deleteArticle(id)
  ElMessage.success('删除成功')
  loadArticles()
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleString('zh-CN')
}
</script>
