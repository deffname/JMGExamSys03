<template>
  <div>
    <div>这是注册界面</div>

    <router-link to="/login">提交信息</router-link>
  </div>
</template>

<script>
  export default{
    created(){
      console.log('register被启动');
    }
  }

</script>