import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken, getdefaultToken } from '@/utils/auth'
// resetRouter函数用于刷新界面
import { resetRouter } from '@/router'

// 返回默认的用户对象
const getDefaultState = () => {
  return {
    token: getdefaultToken(),
    name: '',
    urole: 'student',
    avatar: '',
    rid: ''
  }
}

// 初始化了用户模块的状态，使用了 getDefaultState() 函数返回的默认状态对象。
const state = getDefaultState()

// 包含一系列用于修改用户模块状态的mutations函数
const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_UROLE: (state, urole) => {
    state.urole = urole
    console.log('此时的state ', state)
  },
  SET_URID: (state, rid) => {
    state.rid = rid
  }
}

// 包含了一系列用于处理异步操作的 actions 函数
const actions = {
  // 登录函数，commit是vuex提供的用于提交mutation的方法，userinfo是出入的参数，包括loginform中的各种信息
  login({ commit }, userInfo) {
    // 因为名称一样，所以username/password/......和userInfo里面的信息关联起来
    const { username, password, userrole } = userInfo
    // 下面这是返回的结果
    return new Promise((resolve, reject) => {
      // 这里调用的是登录请求
      login({ username: username.trim(), password: password, userrole: userrole })
        .then(response => {
          const { data } = response
          console.log('登录返回的数据为', data);
          commit('SET_TOKEN', data.accessToken)
          commit('SET_UROLE', data.role)
          commit('SET_NAME', data.username)
          commit('SET_URID', data.rid)
          commit('SET_AVATAR', process.env.VUE_APP_BASE_API + data.avatar)
          console.log('往setToken里面传的参数为', state.name)
          setToken(state.name, data.accessToken)

          // 登录成功之后来一次getinfo

          resolve()
        }).catch(error => {
          reject(error)
        })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          return reject('总之登录失败了，去store/user/getinfo下面找这段话')
        }
        // 讲data里面的数据取出来
        const { username, avatar, identity } = data
        // 将data里面的数据存放到vuex里面去
        commit('SET_NAME', username)
        commit('SET_AVATAR', process.env.VUE_APP_BASE_API + avatar)
        commit('SET_UROLE', identity)
        commit('SET_URID', data.rid)
        console.log('getinfo被触发，此时的state是', state);
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken(state.name) // 先把token移除
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  changerole({ commit, crole }) {
    commit('SET_UROLE', crole)
  }
  ,

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken(state.name) // 必须先把token移除
      commit('RESET_STATE')
      resolve()
    })
  }
}

// namespaced是一个布尔值，当它为true` 时，该模块会拥有其自己的命名空间
// 这意味着它的 state、mutations 和 actions 将只在此模块内部有效，避免与全局或其他模块的命名冲突。
export default {
  namespaced: true,
  state,
  mutations,
  actions
}

