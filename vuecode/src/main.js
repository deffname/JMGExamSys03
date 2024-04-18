import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'; // 因为在index.js里面，所以index.js可以不写
import axios from 'axios';
import router from './router';

// 定义axios发送请求时的默认地址
axios.defaults.baseURL = "http://localhost:8080"
// 将默认地址挂载到Vue身上，一般挂在到Vue身上的属性前面会加$符号
Vue.prototype.$http = axios
// 之后组件中再需要使用axios就不用导入了

Vue.config.productionTip = false
Vue.use(ElementUI);

new Vue({
  render: h => h(App),
  // 如果前后(属性和名称)一样的话，可以简写为store，这样之后在任意一个组件里面都可以通过store去取东西了
  // store是vuex相关的东西
  store: store,
  // router是路由vuerouter相关的东西
  router: router
}).$mount('#app')
