import NProgress from "nprogress";
import router from "./router";
import store from "./store";

NProgress.configure({ showSpinner: false })  // 将进度条中的旋转加载图标设置为不显示
const whiteList = ['/user/login']         // 设置白名单，在下面的路由跳转中使用


router.beforeEach(async (to, from, next) => {
  // 让进度条开始显示
  NProgress.start()
  console.log('from router is ', from.fullPath, ' and to router is ', to.fullPath);
  next()
  NProgress.done()
})