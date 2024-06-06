<template>
  <el-table :data="tableData" strip border style="width: 100%">
    <el-table-column prop="tid" label="学生编号" width="150"> </el-table-column>
    <el-table-column prop="tname" label="学生姓名" width="150">
    </el-table-column>
    <el-table-column prop="tmessage" label="学生信息" width="200">
    </el-table-column>
    <el-table-column prop="handle" label="操作" width="150">
      <!-- slot-scope是接收整行的数据 -->
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row)" type="text" size="small"
          >查看</el-button
        >
        <el-button type="text" size="small">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  created() {
    console.log("查看学生界面启动");
  },
  data() {
    return {
      tableData: [
        {
          tid: 0,
          tmessage: "0000-00-00",
          tname: "测试考试",
        },
        {
          tid: 0,
          tmessage: "0000-00-00",
          tname: "测试考试",
        },
      ],
    };
  },
};
</script>