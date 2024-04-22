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

// 似乎只是设置了一下颜色
<!-- <style lang="scss"> -->
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

// 下面是一个判断，如果判断成功，就把register-container中的el-input中的input输入框设置为指定的颜色
@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .register-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.register-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      // -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

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