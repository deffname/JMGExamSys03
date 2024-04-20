<template>
  <div>
    <div>学生登录</div>
    <div>教师登录</div>
    <div>管理员登录</div>
    <router-link to="/register">还没有账号，注册账号</router-link>
  </div>
</template>



<script>
  export default{
    created(){
      console.log('login被启动');
    }
  }

</script>

