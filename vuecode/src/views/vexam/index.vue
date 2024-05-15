<template>
  <div>
    <el-table :data="tableData" strip border style="width: 100%">
      <el-table-column
        v-for="(row, index) in computedColumns"
        :key="index"
        :label="row.label"
        :prop="row.prop"
        :width="row.width"
      >
      </el-table-column>
      <el-table-column label="操作" width="150" v-if="isNotStudent">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small"
            >查看</el-button
          >
          <el-button type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
      <el-table-column label="学生操作" width="150" v-if="!isNotStudent">
        <template slot-scope="scope">
          <el-button @click="takeExam(scope.row)" type="text" size="small">
            进入考试
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script lang="jsx">
export default {
  created() {
    console.log("查看考试界面启动");
  },
  data() {
    return {
      tableData: [
        {
          eid: 0,
          date: "0000-00-00",
          examname: "测试考试",
          address: "暂缺",
        },
        {
          eid: 0,
          date: "0000-00-00",
          examname: "测试考试",
          address: "暂缺",
        },
      ],

      defaultColumns: [
        {
          prop: "eid",
          label: "考试编号",
          width: "150",
        },
        {
          prop: "date",
          label: "考试日期",
          width: "150",
        },
        {
          prop: "examname",
          label: "考试名称",
          width: "150",
        },
      ],
    };
  },
  computed: {
    urole() {
      return this.$store.getters.urole;
    },
    isNotStudent(){
      return this.urole !='student'
    },
    computedColumns() {
      console.log("vexam计算属性栏被调用");
      const columns = this.defaultColumns;
      return columns;
    },
  },
  methods: {
    handleClick(row) {},
    takeExam(row){
      console.log('学生点击对应考试，要进入考试界面');
      this.$router.push('/studentv/stuexam');
      console.log('跳转到考试界面完成');
    }
  },
};
</script>