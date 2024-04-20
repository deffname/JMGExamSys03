import NProgress from "nprogress";
import router from "./router";
import store from "./store";

NProgress.configure({ showSpinner: false })  // 将进度条中的旋转加载图标设置为不显示
const whiteList = ['/user/login']         // 设置白名单，在下面的路由跳转中使用


