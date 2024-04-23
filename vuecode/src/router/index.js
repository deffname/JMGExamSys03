import VueRouter from "vue-router";
import Vue from "vue";
// 下面导入组件

// 注册
Vue.use(VueRouter)

export const constantRoutes = [

  { path: '*', redirect: '/404', hidden: true },
]

const router = new VueRouter({
  // 将组件和路径链接起来，制定hash属性和组件的对应关系
  // 用到对应组件的地方需要 声明路由占位标签 <router-view></router-view>
  routes: [
    {
      path: '/login',
      component: () => import('../views/login'),
      hidden: true

    },

    {
      path: '/register',
      component: () => import('../views/register'),
      hidden: true
    },

    {
      path: '/',
      redirect: '/login',
      component: () => import('../views/login'),
      hidden: true
    },

    {
      path: '/404',
      component: () => import('@/views/404'),
      hidden: true
    },
    { path: '*', redirect: '/404', hidden: true },

  ]
})

export default router