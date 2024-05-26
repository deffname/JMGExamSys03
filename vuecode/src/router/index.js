import VueRouter from "vue-router";
import Vue from "vue";
// 下面导入组件
import Layout from '@/layout'


// 注册组件
Vue.use(VueRouter)

// 创建一个常量数组，里面放的是这个项目的路由
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('../views/login'),
    hidden: true

  },

  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },

  {
    path: '/adminv',
    component: Layout,
    redirect: '/adminv/vteacher',
    name: 'AdminView',
    meta: { title: 'View', icon: 'el-icon-s-help', belong: 'admin' },
    children: [
      {
        path: 'vteacher',
        name: 'VTeacher',
        component: () => import('@/views/vteacher/index'),
        meta: { title: 'VTeacher', icon: 'table', belong: 'admin' }
      },
      {
        path: 'vexam',
        name: 'VAdminExam',
        component: () => import('@/views/vexam/index'),
        meta: { title: 'VExam', icon: 'tree', belong: 'admin' }
      }
    ]
  },

  {
    path: '/studentv',
    component: Layout,
    redirect: '/studentv/vexam',
    name: 'StuView',
    meta: { title: 'View', icon: 'el-icon-s-help', belong: 'student' },
    children: [
      {
        path: 'vexam',
        name: 'VStuExam',
        component: () => import('@/views/vexam/index'),
        meta: { title: 'VExam', icon: 'tree', belong: 'student' },
      },
      {
        path: 'stuexam',
        name: 'StuExam',
        component: () => import('@/views/vexam/stuexam'),
        hidden: true,
        meta: { title: 'StuExam', icon: 'tree' },
      }

    ]
  },

  {
    path: '/teacherv',
    component: Layout,
    name: 'TeacherV',
    redirect: '/teacherv/vexam',
    meta: { title: 'View', icon: 'tree', belong: 'teacher' },
    children: [
      {
        path: 'vexam',
        component: () => import('@/views/vexam'),
        name: 'TeaVExam',
        meta: { title: 'TeaVExam', icon: 'tree', belong: 'teacher' }
      },
    ]
  },

  {
    path: '/teacherm',
    component: Layout,
    name: 'TeacherM',
    redirect: '/teacherm/vexam',
    meta: { title: 'View', icon: 'tree', belong: 'teacher' },
    children: [
      {
        path: 'cexam',
        component: () => import('@/views/exam/createExam'),
        name: 'CreateExam',
        meta: { title: 'CreateExam', icon: 'tree', belong: 'teacher' }
      }
    ]
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  { path: '*', redirect: '/404', hidden: true },

]

// 创建了一个函数createRouter，这个函数用来创建一个新的vue router实例，其中包含了路由的配置对象
const createRouter = () => new VueRouter({
  // 控制页面滚动行为
  scrollBehavior: () => ({ y: 0 }),
  // 把上面constantRoutes里面的路由导入到这个新的vue router实例中
  routes: constantRoutes
})

// 新建一个vue router实例
const router = createRouter()

// 设置刷新时候要做的事情的代码
// 这个函数用来重置路由器实例，它创建了一个新的路由器实例，
// 然后将当前路由器的matcher属性设置为新路由器的matcher属性，这样可以清除路由器的现有状态，并重新加载路由
export function resetRouter() {
  const newRouter = createRouter()
  // 属性是路由器的匹配器对象，这个对象负责管理路由的匹配规则和路由表
  router.matcher = newRouter.matcher
}

export default router