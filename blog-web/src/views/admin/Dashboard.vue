<template>
  <div>
    <h2>仪表盘</h2>
    <el-row :gutter="16">
      <el-col :span="6">
        <el-statistic title="文章数" :value="stats.articleCount" />
      </el-col>
      <el-col :span="6">
        <el-statistic title="评论数" :value="stats.commentCount" />
      </el-col>
      <el-col :span="6">
        <el-statistic title="分类数" :value="stats.categoryCount" />
      </el-col>
      <el-col :span="6">
        <el-statistic title="标签数" :value="stats.tagCount" />
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDashboard } from '../../api/admin'

const stats = ref({ articleCount: 0, commentCount: 0, categoryCount: 0, tagCount: 0 })

onMounted(async () => {
  const res = await getDashboard()
  stats.value = res.data
})
</script>
