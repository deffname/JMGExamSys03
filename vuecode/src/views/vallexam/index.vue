<template>
  <el-table :data="tableData" strip border style="width: 100%">
    <el-table-column prop="eid" label="考试编号" width="100"> </el-table-column>
    <el-table-column prop="ename" label="考试名称" width="150">
    </el-table-column>
    <el-table-column prop="tid" label="所属教师" width="100"> </el-table-column>
    <el-table-column prop="stime" label="开始时间" width="200">
    </el-table-column>
    <el-table-column prop="etime" label="结束时间" width="200">
    </el-table-column>

    <el-table-column prop="handle" label="设置状态" width="150">
      <!-- slot-scope是接收整行的数据 -->
      <template slot-scope="scope">
        <el-select v-model="nstate" placeholder="设置状态">
          <el-option label="未开始" value="notstart"></el-option>
          <el-option label="进行中" value="starting"></el-option>
          <el-option label="已结束" value="started"></el-option>
        </el-select>
        <el-button @click="setState(scope.row)" type="text" size="small"
          >上传</el-button
        >
      </template>
    </el-table-column>

    <el-table-column prop="handle" label="设置开启时间" width="150">
      <!-- slot-scope是接收整行的数据 -->
      <template slot-scope="scope">
        <el-input v-model="inputtime"></el-input>
        <el-button @click="setSTime(scope.row)" type="text" size="small"
          >上传</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { getAllExam, setStartTime, setEState } from "@/api/admin";
export default {
  created() {
    console.log("查看所有考试界面启动");
    getAllExam()
      .then((res) => {
        const arr = res.data.map((item) => ({
          eid: item.eid,
          ename: item.examname,
          stime: this.formatDate(item.starttime),
          etime: this.formatDate(item.endtime),
          tid: item.tid,
        }));
        this.tableData = arr;
      })
      .catch(() => {
        this.$message.error("获取考试失败，请重新尝试");
      });
  },
  data() {
    return {
      inputtime: "",
      tableData: [],
      nstate: "",
    };
  },
  methods: {
    setSTime(row) {
      setStartTime({ eid: row.eid, utime: this.inputtime })
        .then(() => {
          this.$message({
            message: row.eid + "修改开启时间为" + this.inputtime,
            type: "success",
          });
        })
        .catch(() => {
          this.$message.error("修改失败");
        });
    },
    setState(row) {
      setEState({ eid: row.eid, state: this.nstate })
        .then(() => {
          this.$message({
            message: row.eid + "修改状态为" + this.nstate,
            type: "success",
          });
        })
        .catch(() => {
          this.$message.error("修改失败");
        });
    },
    formatDate(isoString) {
      // 创建一个Date对象
      const date = new Date(isoString);

      // 确保日期是有效的
      if (isNaN(date.getTime())) {
        throw new Error("Invalid date string");
      }

      // 使用Date对象的方法来获取各个部分
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从0开始，所以需要+1，并使用padStart补零
      const day = String(date.getDate()).padStart(2, "0"); // 使用padStart补零
      const hours = String(date.getHours()).padStart(2, "0"); // 使用padStart补零
      const minutes = String(date.getMinutes()).padStart(2, "0"); // 使用padStart补零
      const seconds = String(date.getSeconds()).padStart(2, "0"); // 使用padStart补零

      // 拼接字符串
      const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

      return formattedDate;
    },
  },
};
</script>