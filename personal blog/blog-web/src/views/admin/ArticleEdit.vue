<template>
  <div>
    <h2>{{ isEdit ? '编辑文章' : '写文章' }}</h2>
    <el-form :model="form" label-width="80px" style="max-width:900px">
      <el-form-item label="标题">
        <el-input v-model="form.title" placeholder="请输入文章标题" />
      </el-form-item>
      <el-form-item label="摘要">
        <el-input v-model="form.summary" type="textarea" :rows="2" placeholder="文章摘要（选填）" />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="form.categoryId" placeholder="选择分类" clearable>
          <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="标签">
        <el-select v-model="form.tagIds" multiple placeholder="选择标签">
          <el-option v-for="tag in tags" :key="tag.id" :label="tag.name" :value="tag.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="内容">
        <div style="width:100%;border:1px solid #dcdfe6;border-radius:4px;overflow:hidden">
          <div style="display:flex;border-bottom:1px solid #dcdfe6;background:#f5f7fa">
            <el-button-group>
              <el-button size="small" @click="insertMarkdown('**', '**')">B</el-button>
              <el-button size="small" @click="insertMarkdown('*', '*')">I</el-button>
              <el-button size="small" @click="insertMarkdown('## ', '')">H2</el-button>
              <el-button size="small" @click="insertMarkdown('### ', '')">H3</el-button>
              <el-button size="small" @click="insertMarkdown('- ', '')">列表</el-button>
              <el-button size="small" @click="insertMarkdown('`', '`')">代码</el-button>
              <el-button size="small" @click="insertMarkdown('\n```\n', '\n```\n')">代码块</el-button>
              <el-button size="small" @click="insertMarkdown('[', '](url)')">链接</el-button>
              <el-button size="small" @click="insertMarkdown('> ', '')">引用</el-button>
            </el-button-group>
          </div>
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="20"
            placeholder="支持 Markdown 格式"
            style="textarea"
            ref="editorRef"
          />
        </div>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio :value="0">草稿</el-radio>
          <el-radio :value="1">发布</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit" :loading="loading">
          {{ isEdit ? '更新' : '发布' }}
        </el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getArticleDetail, createArticle, updateArticle } from '../../api/article'
import { getCategories } from '../../api/category'
import { getTags } from '../../api/tag'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)
const loading = ref(false)
const editorRef = ref(null)

const form = ref({
  title: '',
  summary: '',
  content: '',
  categoryId: null,
  tagIds: [],
  status: 1
})
const categories = ref([])
const tags = ref([])

onMounted(async () => {
  const [catRes, tagRes] = await Promise.all([getCategories(), getTags()])
  categories.value = catRes.data
  tags.value = tagRes.data

  if (isEdit.value) {
    const res = await getArticleDetail(route.params.id)
    const article = res.data
    form.value.title = article.title
    form.value.summary = article.summary
    form.value.content = article.content
    form.value.categoryId = article.categoryId
    form.value.status = article.status
    form.value.tagIds = (article.tags || []).map(t => t.id)
  }
})

async function handleSubmit() {
  if (!form.value.title) return ElMessage.warning('请输入标题')
  loading.value = true
  try {
    if (isEdit.value) {
      await updateArticle(route.params.id, form.value)
      ElMessage.success('更新成功')
    } else {
      await createArticle(form.value)
      ElMessage.success('发布成功')
    }
    router.push('/admin/articles')
  } finally {
    loading.value = false
  }
}

function insertMarkdown(prefix, suffix) {
  const textarea = editorRef.value?.$el?.querySelector('textarea')
  if (!textarea) return
  const start = textarea.selectionStart
  const end = textarea.selectionEnd
  const selected = form.value.content.substring(start, end)
  const replacement = prefix + selected + suffix
  form.value.content = form.value.content.substring(0, start) + replacement + form.value.content.substring(end)
}
</script>
