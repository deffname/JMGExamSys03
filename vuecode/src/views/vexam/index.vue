<template>
  <div>
    <el-table
      :data="tableData"
      strip
      border
      style="width: 100%"
      :default-sort="{ prop: 'sdate', order: 'descending' }"
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
      <el-table-column label="操作" width="150" v-if="isNotStudent">
        <template slot-scope="scope">
          <el-button @click="manageExam(scope.row)" type="text" size="small"
            >编辑</el-button
          >
          <el-button @click="deleteExam(scope.row)" type="text" size="small"
            >删除</el-button
          >
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

<script>
import { getExam, deleteExam } from "@/api/teacher";
import { getSExam } from "@/api/student";

export default {
  created() {
    console.log("查看考试界面启动");
    if (this.urole == "teacher") {
      // 如果不是学生就执行下面的命令
      getExam(this.$store.getters.rid)
        .then((response) => {
          // 用map便利整个返回的列表，并创建一个新的列表去储存想要储存的信息
          const transformedData = response.data.map((e) => ({
            eid: e.eid,
            ename: e.examname,
            sdate: this.formatDate(e.starttime),
            edate: this.formatDate(e.endtime),
            estate: e.state,
          }));
          this.tableData = transformedData;
        })
        .catch(() => {
          this.$message.error("请求考试列表失败，请刷新界面重新尝试");
        });
    } else if (this.urole == "student") {
      getSExam()
        .then((response) => {
          const transformedData = response.data.map((e) => ({
            eid: e.eid,
            ename: e.examname,
            sdate: this.formatDate(e.starttime),
            edate: this.formatDate(e.endtime),
            estate: e.state,
          }));
          this.tableData = transformedData;
        })
        .catch(() => {
          this.$message.error("请求考试列表失败，请刷新界面重新尝试");
        });
    }
  },
  data() {
    return {
      exampaper: [],
      tableData: [],
      checkedExam: [],
      defaultColumns: [
        {
          prop: "eid",
          label: "考试编号",
          width: "150",
          sortable: true,
        },
        {
          prop: "ename",
          label: "考试名称",
          width: "150",
          sortable: true,
        },
        {
          prop: "sdate",
          label: "开始时间",
          width: "150",
          sortable: true,
        },
        {
          prop: "edate",
          label: "结束时间",
          width: "150",
          sortable: false,
        },
        {
          prop: "estate",
          label: "考试状态",
          width: "150",
          sortable: true,
        },
      ],
    };
  },
  computed: {
    urole() {
      return this.$store.getters.urole;
    },
    isNotStudent() {
      return this.urole != "student";
    },
    // computedColumns() {
    //   console.log("vexam计算属性栏被调用");
    //   const columns = this.defaultColumns;
    //   if (!this.isNotStudent) {
    //     columns.push();
    //   }
    //   return columns;
    // },
  },
  methods: {
    manageExam(row) {
      console.log("教师编辑考试界面的row = ", row);
      this.$router.push({
        path: "/teacherm/addstu",
        query: { id: row.eid },
      });
    },
    deleteExam(row) {
      deleteExam({ eidl: this.checkedExam })
        .then(() => {
          this.$message({
            message: "删除成功",
            type: "success",
          });
        })
        .catch(() => {
          this.$message.error("未成功删除，请重新尝试");
        });
    },
    handleSelectionChange(val) {
      this.checkedExam = val.map((item) => item.eid);
    },
    takeExam(row) {
      // console.log("学生点击对应考试，要进入考试界面，state = ", row);
      if (row.estate == "starting") {
        this.$router.push({
          path: "/studentv/stuexam",
          query: { row: row },
        });
        console.log("跳转到考试界面完成");
      } else {
        this.$message.error("当前考试未开始，无法进入考试界面");
      }
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