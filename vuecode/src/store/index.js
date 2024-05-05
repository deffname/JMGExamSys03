import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'

Vue.use(Vuex)

// 创建一个vuex store实例
const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user
  },
  getters
})

// 导出创建的vuex store实例，以便在其他地方导入使用
export default store
