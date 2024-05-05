<!-- 用于在侧边栏中展示一个可折叠的 Logo 或标题 -->
<template>
  <!-- 根据 collapse 属性的值来动态添加或删除 collapse 类名。 -->
  <div class="sidebar-logo-container" :class="{'collapse':collapse}">
    <!-- 使用vue组件的transition组件来为logo或标题的显示与隐藏添加过渡效果 -->
    <!-- 过渡效果的名称为name后面的字符串，需要在css中定义具体效果 -->
    <transition name="sidebarLogoFade">
      <!-- 有两个router-link组件，根据collapse的值来决定哪个链接显示 -->
      <!-- v-if指令来判断是否应该显示logo或者标题，有logo就显示logo，否则显示标题 -->
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 v-else class="sidebar-title">{{ title }} </h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 class="sidebar-title">{{ title }} </h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
export default {
  // 定义了组件的名称
  name: 'SidebarLogo',
  props: {
    // 控制logo或标题是否应该被折叠
    collapse: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      title: 'Vue Admin Template',
      logo: 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png'
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 50px;
  line-height: 50px;
  background: #2b2f3a;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 14px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
