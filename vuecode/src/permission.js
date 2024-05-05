import NProgress from "nprogress";
import router from "./router";
import store from "./store";
import { Message } from 'element-ui'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth' // get token from cookie

import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false })  // 将进度条中的旋转加载图标设置为不显示
const whiteList = ['/login', '/register']         // 设置白名单，在下面的路由跳转中使用

router.beforeEach(async (to, from, next) => {
  // 让进度条开始显示
  NProgress.start()
  console.log('from router is ', from.fullPath, ' and to router is ', to.fullPath);

  // set page title
  document.title = getPageTitle(to.meta.title)
  // 获得cookie里面的token
  const hasToken = getToken()

  if (hasToken) {
    // 如果已经有token，证明已经登录过了
    if (to.path === '/login') {
      // 如果要去的界面是login，那么就直接跳转到根界面
      next({ path: '/' })
      NProgress.done()
    } else {
      // 如果要去的界面不是登录界面，首先获取用户的名称
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        // 有名称就下一步
        next()
      } else {
        try {
          // 这里自动发出了调取用户信息的请求
          await store.dispatch('user/getInfo')

          next()
        } catch (error) {
          // 如果出现错误，重置token
          await store.dispatch('user/resetToken')
          // 显示错误信息
          Message.error(error || 'token has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* 如果没有token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // 如果在白名单里面
      next()
      NProgress.done()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }

})