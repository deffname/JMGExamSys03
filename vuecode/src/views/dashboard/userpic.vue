<template>
  <div>
    <div>
      <div style="text-align: center">
        <div class="el-upload">
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

          <!-- avatar ? baseApi + '/file/' + avatar : -->
          <img
            :src="avatar"
            title="点击上传头像"
            class="avatar"
            @click="toggleShow"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import myUpload from "vue-image-crop-upload";
import { mapGetters } from "vuex";
import { getToken } from "@/utils/auth";
import store from "@/store";
import Avatar from "@/assets/404_images/Avatar.jpeg";
export default {
  name: "UserPic",
  components: { myUpload },
  data() {
    return {
      showDialog: false,
      avatar: Avatar,
      activeName: "first",
      saveLoading: false,
      headers: {
        token: getToken(),
      },
      imagecropperKey: 0,
    };
  },
  computed: {
    ...mapGetters(["baseApi"]),
  },
  methods: {
    toggleShow() {
      console.log("toggleShow这个被触发");
      this.showDialog = !this.showDialog;
    },
    // cropUploadSuccess(jsonData, field) {
    //   console.log("cropUploadSuccess这个被触发");
    //   store.dispatch("getInfo").then(() => {});
    // },
    // cropUploadFail(status, field) {
    //   console.log("图片上传失败", status);
    //   console.log("图片上传失败", field);
    // },
    cropSuccess(imgDataUrl, field) {
      console.log("-------- crop success --------");
      console.log(imgDataUrl);
      //把头像设置成上传的图片
      this.avatar = imgDataUrl;
      console.log(field);
    },
    //上传成功回调
    cropUploadSuccess(res, originPicName) {
      //res是后端返回的结果，originPicName是后端接收到图片的名字
      console.log("-------- upload success --------");
      console.log(res);
      this.showDialog = false;
      this.imagecropperKey = this.imagecropperKey + 1;
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

<style>
</style>