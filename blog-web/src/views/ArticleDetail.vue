<template>
  <div class="article-detail" v-if="article">
    <h1>{{ article.title }}</h1>
    <div class="meta">
      <span v-if="article.authorName">{{ article.authorName }}</span>
      <span v-if="article.categoryName"> · {{ article.categoryName }}</span>
      <span> · {{ formatDate(article.createdAt) }}</span>
      <span> · {{ article.viewCount }} 阅读</span>
      <span v-if="article.tags && article.tags.length">
        · <el-tag v-for="tag in article.tags" :key="tag.id" size="small" type="info" style="margin-left:4px">{{ tag.name }}</el-tag>
      </span>
    </div>
    <div class="markdown-body" v-html="renderedContent"></div>
    <CommentSection :article-id="article.id" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleDetail } from '../api/article'
import CommentSection from '../components/CommentSection.vue'
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'
import 'highlight.js/styles/github.css'

const route = useRoute()
const article = ref(null)

const md = new MarkdownIt({
  highlight(str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(str, { language: lang }).value
    }
    return ''
  }
})

const renderedContent = computed(() => {
  return article.value ? md.render(article.value.content || '') : ''
})

onMounted(async () => {
  const res = await getArticleDetail(route.params.id)
  article.value = res.data
})

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('zh-CN')
}
</script>
