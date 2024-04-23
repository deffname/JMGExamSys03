import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon'// svg component

// 全局注册icon-svg
Vue.component('svg-icon', SvgIcon)

// 下面这个是在./svg目录下检索所有svg文件
const req = require.context('./svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)
