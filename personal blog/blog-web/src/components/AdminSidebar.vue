<template>
  <div class="admin-sidebar">
    <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409eff">
      <el-menu-item index="/admin/dashboard">
        <span>仪表盘</span>
      </el-menu-item>
      <el-menu-item index="/admin/articles">
        <span>文章管理</span>
      </el-menu-item>
      <el-menu-item index="/admin/categories">
        <span>分类管理</span>
      </el-menu-item>
      <el-menu-item index="/admin/tags">
        <span>标签管理</span>
      </el-menu-item>
      <el-menu-item index="/admin/comments">
        <span>评论管理</span>
      </el-menu-item>
      <el-menu-item index="/">
        <span>返回前台</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
const route = useRoute()
</script>
