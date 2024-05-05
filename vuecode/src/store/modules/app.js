// 用于管理应用的侧边栏(sidebar)和设备(device)的状态

import Cookies from 'js-cookie'

const state = {
  sidebar: {
    // 表示侧边栏是否打开。如果 cookie 中存在 sidebarStatus，则将其转换为布尔值。
    // 如果不存在，则默认为 true（即侧边栏默认是打开的）
    opened: Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true,
    // 表示侧边栏没有动画
    withoutAnimation: false
  },
  // 表示当前设备的类型
  device: 'desktop'
}

const mutations = {
  // 切换侧边栏的开关状态，并且更新cookie中的值
  TOGGLE_SIDEBAR: state => {
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
    if (state.sidebar.opened) {
      Cookies.set('sidebarStatus', 1)
    } else {
      Cookies.set('sidebarStatus', 0)
    }
  },
  // 关闭侧边栏，并且选择是否带动画
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('sidebarStatus', 0)
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  // 切换设备类型
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
  }
}

const actions = {
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  toggleDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
