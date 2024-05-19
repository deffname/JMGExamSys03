<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ name }}</div>
    <router-link to="/uploadAvatar">上传头像</router-link>
    <el-button @click="showMessage">显示信息</el-button>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "Dashboard",
  created() {
    console.log("dashboard被启动");
  },
  computed: {
    // 获取vuex里面的用户名
    ...mapGetters(["name"]),
  },
  methods: {
    showMessage() {
      console.log(
        "此时process.env.VUE_APP_BASE_API = ",
        process.env.VUE_APP_BASE_API
      );
      console.log("此时process.env = ", process.env);
    },
  },
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
