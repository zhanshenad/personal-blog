<template>
  <div>
    <h2 style="margin-bottom:16px">标签管理</h2>

    <div style="display:flex;gap:8px;margin-bottom:16px">
      <el-input v-model="newTagName" placeholder="标签名称" style="width:200px" @keyup.enter="handleAdd" />
      <el-button type="primary" @click="handleAdd">添加标签</el-button>
    </div>

    <el-table :data="tags" border style="width:100%">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="articleCount" label="文章数" width="100" />
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
import { getTags, createTag, deleteTag } from '../../api/tag'
import { ElMessage, ElMessageBox } from 'element-plus'

const tags = ref([])
const newTagName = ref('')

onMounted(() => loadTags())

async function loadTags() {
  const res = await getTags()
  tags.value = res.data
}

async function handleAdd() {
  if (!newTagName.value.trim()) return ElMessage.warning('请输入标签名称')
  await createTag({ name: newTagName.value })
  ElMessage.success('添加成功')
  newTagName.value = ''
  loadTags()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除该标签？', '提示', { type: 'warning' })
  await deleteTag(id)
  ElMessage.success('删除成功')
  loadTags()
}
</script>
