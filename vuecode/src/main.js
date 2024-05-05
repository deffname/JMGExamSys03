import Vue from 'vue'
import 'normalize.css/normalize.css' // 引入用于统一各个浏览器上界面的依赖

import App from './App.vue'
import store from './store'; // 因为在index.js里面，所以index.js可以不写
import router from './router';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en' // 似乎是一个用于国际化的包（翻译之类的）完成项目之后可以尝试删除

import '@/styles/index.scss' // 引入全局的css设置

import axios from 'axios';

import './permission' // 把路由守卫引入
// import './mock'   // 导入Mock的内容，不需要的时候把这个导入删除就行了
import './icons'  // 导入图标

// 将默认地址挂载到Vue身上，一般挂在到Vue身上的属性前面会加$符号
Vue.prototype.$http = axios
// 之后组件中再需要使用axios就不用导入了

Vue.config.productionTip = false
Vue.use(ElementUI, { locale });


new Vue({
  el: '#app',
  render: h => h(App),
  // 如果前后(属性和名称)一样的话，可以简写为store，这样之后在任意一个组件里面都可以通过store去取东西了
  // store是vuex相关的东西
  store: store,
  // router是路由vuerouter相关的东西
  router: router
}).$mount('#app')
