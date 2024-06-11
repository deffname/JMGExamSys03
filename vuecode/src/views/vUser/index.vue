<template>
  <el-table :data="tableData" strip border style="width: 100%">
    <el-table-column prop="uid" label="用户编号" width="150"> </el-table-column>
    <el-table-column prop="uname" label="用户姓名" width="150">
    </el-table-column>
    <el-table-column prop="urole" label="用户身份" width="200">
    </el-table-column>
    <el-table-column prop="handle" label="修改密码" width="150">
      <!-- slot-scope是接收整行的数据 -->
      <template slot-scope="scope">
        <el-input v-model="newpwd"></el-input>
        <el-button @click="updatePwd(scope.row)" type="text" size="small"
          >修改密码</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { getAllUser, updatePassword } from "@/api/admin";
export default {
  created() {
    console.log("查看用户界面启动");
    getAllUser()
      .then((res) => {
        this.tableData = res.data;
      })
      .catch(() => {
        this.$message.error("未能获取用户列表，请重新尝试");
      });
  },
  data() {
    return {
      diagVisable: false,
      newpwd: "",
      tableData: [],
    };
  },
  methods: {
    updatePwd(row) {
      updatePassword({ uid: row.uid, newpwd: this.newpwd })
        .then(() => {
          this.$message({
            message: row.uid + "修改密码成功",
            type: "success",
          });
        })
        .catch(() => {
          this.$message.error("修改失败");
        });
    },
  },
};
</script>