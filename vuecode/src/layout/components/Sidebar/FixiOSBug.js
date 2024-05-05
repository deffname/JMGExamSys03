// 这个文件是用于修复bug的文件
export default {
  computed: {
    // 一个计算属性，device返回的是当前vuex中存储的device属性
    // 这里是语法糖简化后的写法，完整写法为device: function(){}
    device() {
      return this.$store.state.app.device
    }
  },
  mounted() {
    // 为了修复 iOS 设备上点击菜单时会触发 mouseleave 事件的 bug  
    // https://github.com/PanJiaChen/vue-element-admin/issues/1135
    this.fixBugIniOS()
  },
  methods: {
    // 在mounted中被调用的修复方法
    fixBugIniOS() {
      const $subMenu = this.$refs.subMenu
      if ($subMenu) {
        const handleMouseleave = $subMenu.handleMouseleave
        $subMenu.handleMouseleave = (e) => {
          if (this.device === 'mobile') {
            return
          }
          handleMouseleave(e)
        }
      }
    }
  }
}
