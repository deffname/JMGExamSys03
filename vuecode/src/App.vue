<template>
  <div id="app">
    <!-- <router-link to="/login">转到登录界面</router-link>
    <router-link to="/404">转到404界面</router-link> -->
    <!-- 声明路由占位标签 -->
    <router-view/>
  </div>
</template>

<script>



export default {
  name: 'App',
  created(){
    console.log("app 被创建");
  },
  components: {
    // 这里面包含这个组件里面包含的组件
  }
}
</script>

