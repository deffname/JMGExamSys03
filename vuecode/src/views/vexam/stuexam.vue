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
          <el-button style="width: 100%"> 下载试卷 </el-button>
        </el-col>
      </el-row>
      <el-row style="margin-top: 15px">
        <el-col :span="24">
          <el-button style="width: 100%"> 上传答案 </el-button>
        </el-col>
      </el-row>

      <el-row style="margin-top: 15px">
        <el-col :span="24">
          <el-button style="width: 100%" @click="showMessage">
            显示信息
          </el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    console.log("学生参加考试界面触发", this.$route.query.row);
    this.sdate = this.$route.query.row.sdate;
    this.edate = this.$route.query.row.edate;
    console.log("当前sdate为", this.sdate);
    console.log("当前edate为", this.edate);
    this.updateRemainingTime();
    console.log("当前remaintime为", this.remainingTime, "data.now", Date.now());
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
    };
  },
  methods: {
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
    showMessage() {
      console.log(this.deadline3);
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