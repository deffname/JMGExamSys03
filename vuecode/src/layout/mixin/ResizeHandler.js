// 处理响应式布局和侧边栏的控制，当屏幕尺寸发生变化（电脑到移动端）或者路由发生变化时
import store from '@/store'

// 从全局document对象中获得body元素的引用，body代表html
// document是一个全局对象，用于操作和访问浏览器中的DOM文档
const { body } = document
// WIDTH 是一个常量，表示 992 像素。这通常与 Bootstrap 的响应式设计有关，
// 表示当屏幕宽度小于 992px 时，可能会进入“移动”模式
const WIDTH = 992 // refer to Bootstrap's responsive design

export default {
  // 监听路由，如果是移动端并且侧边栏是打开的，那么就关闭侧边栏
  watch: {
    // route是vue route诸如vue组件中的一个对象，用于在组件内部访问当前路由的信息
    $route(route) {
      if (this.device === 'mobile' && this.sidebar.opened) {
        store.dispatch('app/closeSideBar', { withoutAnimation: false })
      }
    }
  },
  beforeMount() {
    window.addEventListener('resize', this.$_resizeHandler)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.$_resizeHandler)
  },
  mounted() {
    const isMobile = this.$_isMobile()
    if (isMobile) {
      store.dispatch('app/toggleDevice', 'mobile')
      store.dispatch('app/closeSideBar', { withoutAnimation: true })
    }
  },

  methods: {
    // use $_ for mixins properties
    // https://vuejs.org/v2/style-guide/index.html#Private-property-names-essential
    //  `$_isMobile()`: 此方法用于确定当前设备是否为“mobile”
    // 它通过获取 `<body>` 元素的边界框（bounding rectangle）的宽度，并与 `WIDTH` 常量进行比较来实现。
    // 如果宽度小于 `WIDTH`（减去 1 以避免精度问题），则返回 `true`，表示是移动设备。  
    $_isMobile() {
      const rect = body.getBoundingClientRect()
      return rect.width - 1 < WIDTH
    },
    // * `$_resizeHandler()`: 当窗口大小改变时，此方法会被调用。
    // 它首先检查 `document.hidden` 是否为 `false`（即文档不是隐藏的）。
    // 然后，它再次调用 `$_isMobile()` 方法来确定设备类型，并通过 Vuex store 发送动作来切换设备状态。
    // 如果设备是“mobile”，则再次关闭侧边栏（无动画）。
    $_resizeHandler() {
      if (!document.hidden) {
        const isMobile = this.$_isMobile()
        store.dispatch('app/toggleDevice', isMobile ? 'mobile' : 'desktop')

        if (isMobile) {
          store.dispatch('app/closeSideBar', { withoutAnimation: true })
        }
      }
    }
  }
}
