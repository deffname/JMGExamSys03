import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    count: 0,
    todos: [
      { id: 1, text: '吃饭', done: true },
      { id: 2, text: '睡觉', done: false }
    ]
  },
  getters: {
    doneTodos: state => {
      // filter是一个数组方法，用于从数组中选取符合条件的元素
      return state.todos.filter(todo => todo.done)
    }
  },
  mutations: {
    increment(state, n) {
      // n是外界传进来的参数
      state.count += n
    }
  }
})

// 导出store对象 
export default store