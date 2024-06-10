<template>
  <div>
    <div style="padding-top: 20px; padding-bottom: 10px">
      <el-button @click="uploadList">点击上传</el-button>
      <el-button @click="uploadList">文件上传</el-button>
      <el-button @click="sExam">开始考试</el-button>
      <el-button @click="eExam">结束考试</el-button>
      <el-button v-if="stuflag == 'checkedstu'" @click="downloadAns"
        >答案下载</el-button
      >
    </div>

    <el-radio-group v-model="stuflag" size="big" style="margin-bottom: 30px">
      <el-radio-button label="allstu">所有学生</el-radio-button>
      <el-radio-button label="checkedstu">已选学生</el-radio-button>
    </el-radio-group>

    <el-table
      :data="tableData"
      strip
      border
      style="width: 100%"
      :default-sort="{ prop: 'sid', order: 'descending' }"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column
        v-for="(row, index) in defaultColumns"
        :key="index"
        :label="row.label"
        :prop="row.prop"
        :width="row.width"
        :sortable="row.sortable"
      >
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {
  getStudent,
  addStudent,
  getEStudent,
  deleteStu,
  startExam,
  endExam,
  getAnsl,
} from "@/api/teacher";

import { getSExamPaper } from "@/api/student";

export default {
  created() {
    console.log("当前考试的编号是", this.$route.query.id);
    getStudent()
      .then((response) => {
        this.stuList = response.data;
      })
      .catch(() => {
        this.$message.error("获取学生列表出现错误，请刷新界面重新尝试");
      });
  },
  data() {
    return {
      defaultColumns: [
        {
          prop: "sid",
          label: "学生编号",
          width: "150",
          sortable: true,
        },
        {
          prop: "sname",
          label: "学生姓名",
          width: "150",
          sortable: true,
        },
      ],
      stuList: [],
      estuList: [],
      checkedStu: [],
      stuflag: "allstu",
    };
  },
  computed: {
    tableData() {
      console.log("tableData被触发");
      return this.stuflag == "allstu" ? this.stuList : this.estuList;
    },
  },
  watch: {
    stuflag(newVal) {
      if (newVal == "checkedstu") {
        getEStudent(this.$route.query.id)
          .then((response) => {
            this.estuList = response.data;
          })
          .catch(() => {
            this.message.error("请求出错，请重新尝试");
          });
      }
    },
  },
  methods: {
    handleSelectionChange(val) {
      // 当选择项发生变化时触发这个函数
      this.checkedStu = val.map((item) => item.sid);
      console.log("当前选中的学生学号：", this.checkedStu);
    },
    uploadList() {
      if (this.stuflag == "allstu") {
        addStudent({ sidlist: this.checkedStu, eid: this.$route.query.id })
          .then(() => {
            this.$message({
              message: "添加成功",
              type: "success",
            });
          })
          .catch(() => {
            this.$message.error("添加失败，请重新尝试");
          });
      } else if (this.stuflag == "checkedstu") {
        const arr = this.checkedStu.map(
          (item) => this.$route.query.id + "_" + item
        );
        deleteStu({ sekeyl: arr })
          .then(() => {
            this.$message({
              message: "删除成功",
              type: "success",
            });
          })
          .catch(() => {
            this.$message.error("删除失败，请重新尝试");
          });
      }
    },
    sExam() {
      startExam(this.$route.query.id)
        .then(() => {
          this.$message({
            message: "开启对应考试成功",
            type: "success",
          });
        })
        .catch((error) => {
          this.$message.error(error.msg);
        });
    },
    eExam() {
      endExam(this.$route.query.id)
        .then(() => {
          this.$message({
            message: "结束对应考试成功",
            type: "success",
          });
        })
        .catch((error) => {
          this.$message.error(error.msg);
        });
    },
    downloadAns() {
      console.log(
        "发送获取答案前，此时考试id为",
        this.$route.query.id,
        " ",
        this.checkedStu
      );
      const sekeyl = this.checkedStu.map((item) => {
        return this.$route.query.id + "_" + item;
      });
      console.log("sekeyl = ", sekeyl);
      getAnsl({ sekeyl: sekeyl })
        .then((response) => {
          console.log(response.data);
          const ansl = response.data;
          for (let key in ansl) {
            if (ansl.hasOwnProperty(key) && ansl[key] !== null) {
              console.log(`Key ${key} has a value: ${ansl[key]}`);
              // 进行文件下载
              var url =
                process.env.VUE_APP_BASE_API +
                "/downloadEPaper?filePath=" +
                ansl[key];
              window.open(url);
            } else if (ansl.hasOwnProperty(key) && ansl[key] == null) {
              this.$notify({
                title: "提示",
                message: "学号为" + key + "的学生尚未提交答案",
                duration: 0,
              });
            }
          }
        })
        .catch(() => {
          this.$message.error("获取答案失败，请重新尝试");
        });
    },
  },
};
</script>

<style>
</style>