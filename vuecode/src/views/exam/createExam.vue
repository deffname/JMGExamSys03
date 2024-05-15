<template>
  <el-form ref="form" :model="createExamData" label-width="80px">
    <el-form-item label="考试名称">
      <el-input v-model="createExamData.ename" style="width: 50%"></el-input>
    </el-form-item>
    <el-form-item label="考试时间">
      <el-time-picker
        v-model="createExamData.estart"
        :picker-options="{
          selectableRange: '18:30:00 - 20:30:00',
        }"
        placeholder="任意时间点"
      >
      </el-time-picker>
      <el-time-picker
        v-model="createExamData.eend"
        :picker-options="{
          selectableRange: '18:30:00 - 20:30:00',
        }"
        placeholder="任意时间点"
      >
      </el-time-picker></el-form-item
  ></el-form>
</template>
    
    </el-form-item>
  </el-form>
</template>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  created() {
    console.log("教师创建考试界面启动");
  },
  data() {
    return {
      createExamData: {
        ename: "",
        estart: "",
        eend: "",
      },
    };
  },
};
</script>

<style>
</style>