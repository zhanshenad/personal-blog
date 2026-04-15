<template>
  <div class="article-card" @click="$router.push(`/article/${article.id}`)">
    <h2>{{ article.title }}</h2>
    <div class="meta">
      <span v-if="article.authorName">{{ article.authorName }}</span>
      <span v-if="article.categoryName"> · {{ article.categoryName }}</span>
      <span> · {{ formatDate(article.createdAt) }}</span>
      <span> · {{ article.viewCount }} 阅读</span>
    </div>
    <div class="summary">{{ article.summary }}</div>
    <div class="tags" v-if="article.tags && article.tags.length">
      <el-tag v-for="tag in article.tags" :key="tag.id" size="small" type="info" style="margin-right:6px">
        {{ tag.name }}
      </el-tag>
    </div>
  </div>
</template>

<script setup>
defineProps({ article: { type: Object, required: true } })

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('zh-CN')
}
</script>
