import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/login/register.vue')
  },
  {
    path: '/forgotPassword',
    name: 'ForgotPassword',
    component: () => import('@/views/login/forgotPassword.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/dashboard/index.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/maintain/user',
    name: 'UserInfo',
    component: () => import('@/views/maintain/user/index.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/assets/manage',
    name: 'CarbonAssetsManage',
    component: () => import('@/views/assets/manage/index.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/assets/develop',
    name: 'CarbonAssetsDevelop',
    component: () => import('@/views/assets/develop/index.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/trade/manage',
    name: 'CarbonTradeManage',
    component: () => import('@/views/trade/manage/index.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/news',
    name: 'CarbonNews',
    component: () => import('@/views/news/index.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/system/manage',
    name: 'SystemManage',
    component: () => import('@/views/system/manage/index.vue'),
    meta: { requiresAuth: true }
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 需要登录的页面
    if (!token) {
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    } else {
      next()
    }
  } else {
    // 不需要登录的页面
    if (token && (to.path === '/login' || to.path === '/register' || to.path === '/forgotPassword')) {
      // 已登录用户访问登录/注册/忘记密码页面，跳转到首页
      next('/home')
    } else {
      next()
    }
  }
})

export default router


