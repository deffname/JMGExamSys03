<template>
  <el-form ref="form" :model="cExamData" label-width="80px">
    <el-form-item label="考试名称" style="margin-top: 30px">
      <el-input v-model="cExamData.examname" style="width: 50%"></el-input>
    </el-form-item>
    <el-form-item label="考试时间">
      <div class="block">
        <el-date-picker
          v-model="cExamData.edate"
          type="date"
          placeholder="选择日期"
        >
        </el-date-picker>
      </div>
      <div style="margin-top: 10px">
        <el-time-select
          placeholder="起始时间"
          v-model="cExamData.starttime"
          :picker-options="{
            start: '08:30',
            step: '00:15',
            end: '18:30',
          }"
        >
        </el-time-select>
        <el-time-select
          placeholder="结束时间"
          v-model="cExamData.endtime"
          :picker-options="{
            start: '08:30',
            step: '00:15',
            end: '18:30',
            minTime: cExamData.starttime,
          }"
          style="margin-left: 10px"
        >
        </el-time-select>
      </div>
    </el-form-item>
    <el-form-item label="试卷上传">
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
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">
          上传 doc || docx || pdf 格式文件
        </div>
      </el-upload>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="confirmUpload">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>
    
<script>
import { getToken } from "@/utils/auth";
import axios from "axios";
import { createExam } from "@/api/teacher";
export default {
  created() {
    console.log("教师创建考试界面启动");
  },
  data() {
    return {
      cExamData: {
        examname: "",
        edate: "",
        starttime: "",
        endtime: "",
      },
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

    async confirmUpload() {
      console.log("开始上传");

      try {
        // avait暂停函数执行，如果出现错误就跳转到catch里，否则就执行下面的内容
        const res = await createExam({
          examname: this.cExamData.examname,
          starttime: this.cExamData.starttime,
          endtime: this.cExamData.endtime,
          edate: this.cExamData.edate.toISOString().slice(0, 10),
        });
        this.$message({
          message: "考试创建成功！",
          duration: 1000,
        });

        const { data } = res;
        console.log("获取到的考试编号是", data);
        var param = new FormData();
        this.fileList.forEach((val, index) => {
          // 根据需要更改字段名，例如："file" + (index + 1)
          param.append("file", val.raw);
        });

        try {
          const response = await axios.post(
            process.env.VUE_APP_BASE_API + "/upefile",
            param,
            {
              headers: {
                token: getToken(this.$store.getters.name),
                eid: data,
              },
            }
          );
          // 在这里处理响应数据
          this.$message({
            message: "试卷文件上传成功！",
            duration: 1000,
          });
        } catch (error) {
          this.$message({
            message: "试卷文件上传失败",
            type: "error",
            duration: 1000,
          });
          console.error("试卷文件上传失败:", error); // 调试信息
        }

        this.$router.push({
          path: "/teacherm/addstu",
          query: { id: data },
        });
      } catch (error) {
        this.$message({
          message: "考试创建失败",
          type: "error",
          duration: 1000,
        });
        console.error("考试创建失败:", error); // 调试信息
      }
    },
  },
};
</script>





<style>
</style>