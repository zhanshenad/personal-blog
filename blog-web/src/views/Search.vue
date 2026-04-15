<template>
  <div style="max-width:900px;margin:0 auto">
    <h2 style="margin-bottom:20px">搜索: "{{ keyword }}"</h2>
    <div v-for="article in articles" :key="article.id">
      <ArticleCard :article="article" />
    </div>
    <div v-if="loaded && !articles.length" style="text-align:center;padding:40px;color:#999">
      没有找到相关文章
    </div>
    <div class="pagination" v-if="total > pageSize">
      <el-pagination
        v-model:current-page="page"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        @current-change="doSearch"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { searchArticles } from '../api/article'
import ArticleCard from '../components/ArticleCard.vue'

const route = useRoute()
const keyword = ref('')
const articles = ref([])
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loaded = ref(false)

onMounted(() => {
  keyword.value = route.query.keyword || ''
  doSearch()
})

watch(() => route.query.keyword, (val) => {
  keyword.value = val || ''
  page.value = 1
  doSearch()
})

async function doSearch() {
  if (!keyword.value) return
  loaded.value = false
  const res = await searchArticles({ keyword: keyword.value, page: page.value, size: pageSize.value })
  articles.value = res.data.records
  total.value = Number(res.data.total)
  loaded.value = true
}
</script>
