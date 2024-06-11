<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <el-radio-group
        class="title-container"
        v-model="loginForm.userrole"
        size="big"
        style="margin-bottom: 30px"
      >
        <el-radio-button label="student">学生</el-radio-button>
        <el-radio-button label="teacher">教师</el-radio-button>
        <el-radio-button label="admin">管理员</el-radio-button>
      </el-radio-group>

      <!-- placeholder是默认显示的文本 -->
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          :placeholder="loginForm.userrole + ' name'"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <!-- 这是一个图标 -->
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <!-- 这是密码的输入框 -->
        <!-- ref给输入框设置一个引用名称 -->
        <!-- @keyup.enter.native="handleLogin"表示按下enter键时触发handleLogin方法 -->
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <!-- 这是密码输入框右边那个眼睛图标 -->
        <span class="show-pwd" @click="showPwd">
          <svg-icon
            :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
          />
        </span>
      </el-form-item>

      <el-form-item>
        <el-button
          :loading="loading"
          type="primary"
          style="width: 100%"
          @click="handleLogin"
          >用户登录</el-button
        >
      </el-form-item>
      <!-- @就是 v-on 是vue对onclick的封装 -->
      <el-form-item>
        <el-button
          :loading="loading"
          style="width: 100%"
          @click="handleRegister"
          >{{ userroleC }}注册</el-button
        >
      </el-form-item>

      <div class="tips">
        <span style="margin-right: 20px">username: admin</span>
        <span> password: any</span>
      </div>
    </el-form>
  </div>
</template>

<script>
import { validUsername } from "@/utils/validate";

export default {
  name: "Login",
  created() {
    console.log("login被调用");
  },
  data() {
    // 用来判断是不是一个合法的用户名
    const validateUsername = (rule, value, callback) => {
      // 这里的rule是当前要验证的对象，value是验证的字段的值
      if (!validUsername(value)) {
        callback(new Error("Please enter the correct user name"));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      // 这里只对密码长度进行了判断
      if (value.length < 6) {
        callback(new Error("The password can not be less than 6 digits"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        // 这是设置的默认值
        username: "admin",
        password: "111111",
        userrole: "student",
      },
      loginRules: {
        // username规则为必须填写，且在失去焦点时触发验证，并且使用自定义的validdateUsername方法进行验证
        // validator 属性的作用是指定一个用于验证字段的自定义验证函数
        username: [
          { required: true, trigger: "blur", validator: validateUsername },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
      },
      loading: false, //控制登录按钮的加载状态，默认为false
      passwordType: "password", // 密码输入框的类型，默认为possword
      redirect: undefined, // 未定义的重定向参数
      userroleC: "学生",
    };
  },
  watch: {
    // 监听路由对象的变化
    $route: {
      // handler函数会在 route发生变动的时候被调用，接收一个新的route，代表新的路由对象
      handler: function (route) {
        // 检测新的路由对象是否有query属性 && 检测新的路由对象是否有redirect，如果有就把这个值赋给组件中的redir属性
        // 路由对象的query属性是指URL中的查询参数部分，即?后面的部分
        this.redirect = route.query && route.query.redirect;
      },
      // 表示组件创建时立刻立刻执行一次这个函数
      immediate: true,
    },
    "loginForm.userrole": function (newVal) {
      console.log("userrole watch 被调用");
      if (newVal == "student") {
        this.userroleC = "学生";
      }
      if (newVal == "teacher") {
        this.userroleC = "教师";
      }
      if (newVal == "admin") {
        this.userroleC = "管理员";
      }
    },
  },
  methods: {
    // 用于切换密码框的状态
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      // 在密码输入框内容更新后，将焦点设置到密码输入框上，以便用户可以立即输入密码
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    handleLogin() {
      // validate是一个自动实现的函数，被调用时会自动读取loginForm中的每个变量的validator用于比对
      // valid是验证结果，如果表单验证通过，valid是true，否则是false
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 让loading图标开始旋转
          this.loading = true;
          // 调用vuex中的store中的action，如果登录成功就把用户送到根目录或者重定向的目录下面
          this.$store
            .dispatch("user/login", this.loginForm)
            .then(() => {
              // const usrole = this.$store.state.userrole
              console.log("登录提交信息成功");
              this.$router.push({ path: "/" });
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
      // this.$router.push({ path: "/admin" });
    },
    handleRegister() {
      console.log("handleRegister被启动");
      // 在vuex中改变当前用户的urole值
      this.$store.commit("user/SET_UROLE", this.loginForm.userrole);
      // 指挥路由跳转到注册界面
      this.$router.push({ path: "/register" });
      // this.$router.push({ path: '/register' })
    },
  },
};
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
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

<!-- scoped代表将样式限定在当前组件内部，不污染其他组件 -->
<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative; // 设置元素定位为相对定位，意味着元素在文档流中仍然占据原本的空间，可以使用top, right等属性进行位置偏移
    width: 520px; // 设置元素的宽度
    max-width: 100%; // 设置元素的最大宽度，确保元素在不同屏幕尺寸下自适应宽度，不会超过父容器的宽度
    padding: 160px 35px 0; // 设置元素内边距(上，右，下，左)
    margin: 0 auto; // 设置元素的外边距，上下为0，左右为自动（居中）
    overflow: hidden; // 设置元素溢出内容隐藏，如果元素内部内容超过指定的宽度和高度，超出部分将被隐藏
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
