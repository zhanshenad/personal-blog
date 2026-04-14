import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('../views/Home.vue') },
  { path: '/article/:id', name: 'ArticleDetail', component: () => import('../views/ArticleDetail.vue') },
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('../views/Register.vue') },
  { path: '/search', name: 'Search', component: () => import('../views/Search.vue') },
  {
    path: '/admin',
    redirect: '/admin/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/admin/Dashboard.vue'), meta: { requiresAuth: true } },
      { path: 'articles', name: 'ArticleManage', component: () => import('../views/admin/ArticleManage.vue'), meta: { requiresAuth: true } },
      { path: 'articles/create', name: 'ArticleCreate', component: () => import('../views/admin/ArticleEdit.vue'), meta: { requiresAuth: true } },
      { path: 'articles/edit/:id', name: 'ArticleEditPage', component: () => import('../views/admin/ArticleEdit.vue'), meta: { requiresAuth: true } },
      { path: 'categories', name: 'CategoryManage', component: () => import('../views/admin/CategoryManage.vue'), meta: { requiresAuth: true } },
      { path: 'tags', name: 'TagManage', component: () => import('../views/admin/TagManage.vue'), meta: { requiresAuth: true } },
      { path: 'comments', name: 'CommentManage', component: () => import('../views/admin/CommentManage.vue'), meta: { requiresAuth: true } },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
    const token = localStorage.getItem('token')
    if (!token) {
      next('/login')
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
