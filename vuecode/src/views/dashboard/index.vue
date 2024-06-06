<template>
  <div class="dashboard-container">
    <el-row :gutter="40">
      <el-col
        :xs="24"
        :sm="24"
        :md="10"
        :lg="9"
        :xl="8"
        style="margin-bottom: 10px"
      >
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>

          <div class="dashboard-text">name: {{ name }}</div>

          <div>头像</div>
          <div style="margin-top: 10px">
            <my-upload
              :modelValue.sync="showDialog"
              :key="imagecropperKey"
              lang-type="zh"
              :headers="headers"
              @crop-success="cropSuccess"
              @crop-upload-success="cropUploadSuccess"
              @crop-upload-fail="cropUploadFail"
              :url="baseApi + '/uppic'"
            ></my-upload>
            <img
              :src="avatar ? avatar : default_avatar"
              title="点击上传头像"
              class="avatar"
              @click="toggleShow"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <div>
          <el-button @click="showMessage">显示信息</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import myUpload from "vue-image-crop-upload";
import { getToken } from "@/utils/auth";
import { mapGetters } from "vuex";
import Avatar from "@/assets/404_images/avatar.png";
export default {
  name: "Dashboard",
  components: { myUpload },
  created() {
    console.log("dashboard被启动");
  },
  data() {
    return {
      showDialog: false,
      default_avatar: Avatar,
      activeName: "first",
      saveLoading: false,
      headers: {
        token: getToken(),
      },
      imagecropperKey: 0,
    };
  },
  computed: {
    // 获取vuex里面的用户名
    ...mapGetters(["name", "baseApi", "avatar"]),
  },
  methods: {
    showMessage() {
      console.log("此时avatar =  ", this.$store.getters.avatar);
      console.log("此时process.env = ", process.env);
    },
    toggleShow() {
      console.log("toggleShow这个被触发");
      this.showDialog = !this.showDialog;
    },
    cropSuccess(imgDataUrl, field) {
      console.log("-------- crop success --------");
      console.log("头像被设置为", imgDataUrl);
      //把头像设置成上传的图片
      this.default_avatar = imgDataUrl;
      console.log(field);
    },
    //上传成功回调
    cropUploadSuccess(res, originPicName) {
      //res是后端返回的结果，originPicName是后端接收到图片的名字
      console.log("-------- upload success --------");
      console.log(res);
      this.showDialog = false;
      this.imagecropperKey = this.imagecropperKey + 1;

      this.$store.dispatch("user/getInfo");
    },
    //上传失败回调
    cropUploadFail(status, field) {
      console.log("-------- upload fail --------");
      console.log("上传失败状态" + status);
      console.log("field: " + field);
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
