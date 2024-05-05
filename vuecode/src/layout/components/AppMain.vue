<!-- 用于在单页面应用（SPA）中渲染不同的视图（或页面），
并且当路由改变时，它使用了一个过渡效果来平滑地切换视图 -->
<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <!-- vue router提供的组件，动态绑定key，当key值发生改变时，会刷新router-view -->
      <router-view :key="key" />
    </transition>
  </section>
</template>

<script>
export default {
  name: "AppMain",
  computed: {
    key() {
      // vue router会在路由更新时改变$route
      return this.$route.path;
    },
  },
};
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}
.fixed-header + .app-main {
  padding-top: 50px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
