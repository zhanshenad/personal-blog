<template>
  <div class="home-layout">
    <div style="flex:1">
      <div v-for="article in articles" :key="article.id">
        <ArticleCard :article="article" />
      </div>
      <div v-if="!articles.length" style="text-align:center;padding:40px;color:#999">暂无文章</div>
      <div class="pagination">
        <el-pagination
          v-model:current-page="page"
          :page-size="pageSize"
          :total="total"
          layout="prev, pager, next"
          @current-change="loadArticles"
        />
      </div>
    </div>
    <div class="sidebar">
      <div class="sidebar-widget">
        <h3>搜索</h3>
        <el-input v-model="searchKeyword" placeholder="搜索文章..." @keyup.enter="doSearch">
          <template #append>
            <el-button @click="doSearch">搜索</el-button>
          </template>
        </el-input>
      </div>
      <div class="sidebar-widget">
        <h3>分类</h3>
        <div v-for="cat in categories" :key="cat.id" style="margin-bottom:6px">
          <a href="#" @click.prevent="filterByCategory(cat.id)">
            {{ cat.name }} ({{ cat.articleCount || 0 }})
          </a>
        </div>
      </div>
      <div class="sidebar-widget">
        <h3>标签</h3>
        <div class="tag-cloud">
          <el-tag v-for="tag in tags" :key="tag.id" size="small" style="cursor:pointer" @click="filterByTag(tag.id)">
            {{ tag.name }}
          </el-tag>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getArticles } from '../api/article'
import { getCategories } from '../api/category'
import { getTags } from '../api/tag'
import ArticleCard from '../components/ArticleCard.vue'

const router = useRouter()
const articles = ref([])
const categories = ref([])
const tags = ref([])
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchKeyword = ref('')

onMounted(() => {
  loadArticles()
  loadCategories()
  loadTags()
})

async function loadArticles() {
  const res = await getArticles({ page: page.value, size: pageSize.value })
  articles.value = res.data.records
  total.value = Number(res.data.total)
}

async function loadCategories() {
  const res = await getCategories()
  categories.value = res.data
}

async function loadTags() {
  const res = await getTags()
  tags.value = res.data
}

function doSearch() {
  if (searchKeyword.value.trim()) {
    router.push({ path: '/search', query: { keyword: searchKeyword.value } })
  }
}

function filterByCategory(categoryId) {
  router.push({ path: '/', query: { categoryId } })
}

function filterByTag() {
  // Simple: navigate to home (tag filtering can be extended)
}
</script>
