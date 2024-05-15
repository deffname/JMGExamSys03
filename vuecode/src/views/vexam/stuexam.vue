<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="14">
        <!-- shadow表示鼠标悬停在这里时显示阴影，统计组件显示的值是deadline3 -->
        <el-card shadow="hover" style="width: 100%; margin-top: 20px">
          <div style="width: 100%; display: inline-block">
            <el-statistic
              @finish="hilarity"
              :value="deadline3"
              time-indices
              title="距离考试结束还有"
            >
            </el-statistic>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  created() {
    console.log("学生参加考试界面触发");
  },
  data() {
    return {
      deadline3: Date.now() + 1000 * 60 * 30,
    };
  },
  methods: {
    hilarity() {
      this.$notify({
        title: "提示",
        message: "时间已到",
        duration: 0,
      });
    },
  },
};
</script>

<style>
</style>