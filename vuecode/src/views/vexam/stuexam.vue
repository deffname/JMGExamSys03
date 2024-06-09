<template>
  <div class="centered-container">
    <div style="width: 80%">
      <el-row>
        <el-col :span="24">
          <!-- shadow表示鼠标悬停在这里时显示阴影，统计组件显示的值是remainingTime -->
          <el-card shadow="hover" style="width: 100%; margin-top: 20px">
            <div style="width: 100%; display: inline-block">
              <el-statistic
                @finish="hilarity"
                :value="remainingTime"
                time-indices
                :title="timeTitle"
              >
              </el-statistic>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row style="margin-top: 15px">
        <el-col :span="24">
          <el-button style="width: 100%" @click="downloadEPaper">
            下载试卷
          </el-button>
        </el-col>
      </el-row>

      <el-row style="margin-top: 15px">
        <el-col :span="24">
          <el-upload
            class="upload-demo"
            action="#"
            drag
            multiple
            :headers="headers"
            :auto-upload="false"
            :file-list="fileList"
            :on-change="handleChange"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div class="el-upload__tip" slot="tip">
              上传 doc || docx || pdf 格式文件
            </div>
          </el-upload>
        </el-col>
      </el-row>

      <el-row style="margin-top: 15px">
        <el-col :span="24">
          <el-button style="width: 100%" @click="uploadAns">
            上传答案
          </el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { getToken } from "@/utils/auth";
import { getSExamPaper } from "@/api/student";

export default {
  created() {
    console.log("学生参加考试界面触发", this.$route.query.row);

    this.sdate = this.$route.query.row.sdate;
    this.edate = this.$route.query.row.edate;
    console.log("当前sdate为", this.sdate);
    console.log("当前edate为", this.edate);
    this.updateRemainingTime();
  },
  beforeDestroy() {
    clearInterval(this.timer); // 组件销毁前清除定时器
  },
  data() {
    return {
      sdate: "",
      edate: "",
      remainingTime: "",
      timeTitle: "距离考试开始还有",
      fileList: [],
      headers: {
        "Content-Type": "multipart/form-data",
      },
    };
  },
  methods: {
    handleChange(file, fileList) {
      //文件数量改变
      this.fileList = fileList;
    },
    downloadEPaper() {
      // const param = { filepath: this.$route.query.row.exampaper };
      getSExamPaper(this.$route.query.row.eid)
        .then((res) => {
          var url =
            process.env.VUE_APP_BASE_API +
            "/downloadEPaper?filePath=" +
            res.data.exampaper;
          window.open(url);
        })
        .catch(() => {
          this.$message.error("获取试卷地址失败");
        });

      // axios.get(process.env.VUE_APP_BASE_API + this.$route.query.row.exampaper);
    },
    hilarity() {
      if (this.timeTitle == "距离考试开始还有") {
        this.updateRemainingTime();
      } else if (this.timeTitle == "考试已结束") {
        this.$notify({
          title: "提示",
          message: "时间已到",
          duration: 0,
        });
      }
    },

    updateRemainingTime() {
      const now = new Date();
      console.log("更新remaintime启动");
      console.log("sdate", new Date(this.sdate));
      if (now < new Date(this.sdate)) {
        console.log("考试尚未开始");
        // 如果当前时间小于开始时间，计算距离开始时间的剩余时间
        this.remainingTime = new Date(this.sdate);
        this.timeTitle = "距离考试开始还有";
      } else if (now < new Date(this.edate)) {
        console.log("考试进行中");
        // 如果当前时间在开始时间和结束时间之间，计算距离结束时间的剩余时间
        this.remainingTime = new Date(this.edate);
        this.timeTitle = "距离考试结束还有";
      } else {
        console.log("考试已经结束");
        // 如果考试已经结束，可以显示"考试已结束"或其他信息
        this.remainingTime = 0;
        this.timeTitle = "考试已结束";
      }
    },

    uploadAns() {
      var param = new FormData();
      this.fileList.forEach((val, index) => {
        // 根据需要更改字段名，例如："file" + (index + 1)
        param.append("file", val.raw);
      });

      axios
        .post(process.env.VUE_APP_BASE_API + "/upAnsfile", param, {
          headers: {
            token: getToken(),
            sekey: this.$route.query.row.eid + "_" + this.$store.getters.rid,
          },
        })
        .then(() => {
          this.$message({
            message: "上传答案成功",
            type: "success",
          });
        })
        .catch(() => {
          this.$message.error("答案上传失败，请重新尝试");
        });
    },
  },
};
</script>

<style scoped>
.centered-container {
  display: flex;
  justify-content: center;

  height: 100vh;
  width: 100%;
}
</style>