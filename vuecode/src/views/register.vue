<template>
  <div class="register-container">
    <el-form
      class="register-form"
      ref="registerForm"
      :model="registerForm"
      label-width="100px"
      label-position="left"
    >
      <el-form-item label="用户姓名" prop="username">
        <el-input v-model="registerForm.username"></el-input>
      </el-form-item>

      <el-form-item label="真实姓名" prop="fullname">
        <el-input v-model="registerForm.fullname"></el-input>
      </el-form-item>

      <el-form-item label="账号密码" prop="password">
        <el-input v-model="registerForm.password"></el-input>
        <!-- <svg><use xlink:href="#eye" /></svg> -->
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      registerForm: {
        username: "",
        fullname: "",
        password: "",
      },
      registerRules: {},
    };
  },
  created() {
    console.log("register被启动");
  },
  methods: {
    onSubmit() {
      this.$router.push({ path: "login" });
    },
  },
};
</script>


<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.register-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .register-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }
}
</style>