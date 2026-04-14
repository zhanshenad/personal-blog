<template>
  <div>
    <h2 style="margin-bottom:16px">分类管理</h2>

    <div style="display:flex;gap:8px;margin-bottom:16px">
      <el-input v-model="newCategory.name" placeholder="分类名称" style="width:200px" />
      <el-input v-model="newCategory.sortOrder" placeholder="排序" type="number" style="width:100px" />
      <el-button type="primary" @click="handleAdd">添加分类</el-button>
    </div>

    <el-table :data="categories" border style="width:100%">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="articleCount" label="文章数" width="100" />
      <el-table-column prop="sortOrder" label="排序" width="80" />
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button size="small" @click="startEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="editDialogVisible" title="编辑分类" width="400px">
      <el-form :model="editForm" label-width="60px">
        <el-form-item label="名称">
          <el-input v-model="editForm.name" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="editForm.sortOrder" type="number" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUpdate">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCategories, createCategory, updateCategory, deleteCategory } from '../../api/category'
import { ElMessage, ElMessageBox } from 'element-plus'

const categories = ref([])
const newCategory = ref({ name: '', sortOrder: 0 })
const editDialogVisible = ref(false)
const editForm = ref({ id: null, name: '', sortOrder: 0 })

onMounted(() => loadCategories())

async function loadCategories() {
  const res = await getCategories()
  categories.value = res.data
}

async function handleAdd() {
  if (!newCategory.value.name) return ElMessage.warning('请输入分类名称')
  await createCategory(newCategory.value)
  ElMessage.success('添加成功')
  newCategory.value = { name: '', sortOrder: 0 }
  loadCategories()
}

function startEdit(row) {
  editForm.value = { id: row.id, name: row.name, sortOrder: row.sortOrder }
  editDialogVisible.value = true
}

async function handleUpdate() {
  await updateCategory(editForm.value.id, editForm.value)
  ElMessage.success('更新成功')
  editDialogVisible.value = false
  loadCategories()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确定删除该分类？', '提示', { type: 'warning' })
  await deleteCategory(id)
  ElMessage.success('删除成功')
  loadCategories()
}
</script>
