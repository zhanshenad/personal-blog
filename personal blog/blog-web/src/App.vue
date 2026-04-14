<template>
  <div id="app">
    <Navbar v-if="!isAdminRoute" />
    <div :class="['main-container', { 'admin-layout': isAdminRoute }]">
      <AdminSidebar v-if="isAdminRoute" />
      <main :class="{ 'admin-content': isAdminRoute }">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import Navbar from './components/Navbar.vue'
import AdminSidebar from './components/AdminSidebar.vue'

const route = useRoute()
const isAdminRoute = computed(() => route.path.startsWith('/admin'))
</script>
