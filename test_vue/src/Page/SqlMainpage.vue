<template>
  <div>
    <div class="header">
      <el-button type="primary" @click="createTable">创建数据表</el-button>
    </div>
    <div class="table-container">
      <h2 class="table-title">所有数据表</h2>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="表格编号" width="180"></el-table-column>
        <el-table-column prop="name" label="表格名称" width="180"></el-table-column>
        <el-table-column prop="permission" label="共享状态" width="180" :formatter="formatPermission"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-link type="primary" @click="editRow(scope.row.name,scope.row.permission)">编辑</el-link>
            <el-link type="primary" @click="viewDetails(scope.row.name)" style="margin-left: 10px;">查看详情</el-link>
            <el-link type="primary" @click="deleteRow(scope.row.name)" style="margin-left: 10px;">删除</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [
        { id:1, name: '2016-05-02', permission: true },
        { id:2, name: '2016-05-04', permission: false },
        { id:3, name: '2016-05-01', permission: true },
        { id:4, name: '2016-05-03', permission: false }
      ]
    };
  },
  methods: {
    formatPermission(row, column, cellValue) {
      return cellValue ? '非共享' : '共享';
    },
    createTable() {
      console.log('创建数据表');
      this.$router.push({ name: 'CreateTable' });
    },
    editRow(name, per) {
      this.$router.push({ name: 'EditTable', params: { tableName: name, state: per } });
    },
    viewDetails(name) {
      this.$router.push({ name: 'DataTable', params: { tableName: name } });
    },
    deleteRow(name) {
      this.tableData = this.tableData.filter(table => table.name !== name);
      this.$axios.post(`/modify_database/delete_table?name=${name}`)
        .then(() => {
          this.$message.success('删除成功');
          this.tableData = this.tableData.filter(table => table.name !== name);
        })
        .catch(error => {
          if (error.response && error.response.status === 400) {
            this.$message.error('表格不存在');
          } else {
            this.$message.error('删除失败');
          }
          console.error('删除失败:', error);
        });
    },
    fetchTableData() {
      this.$axios.get('/modify_database/tables')
        .then(response => {
          this.tableData = response.data;
        })
        .catch(error => {
          console.error('获取表格数据失败:', error);
        });
    },
  },
  created() {
    this.fetchTableData();
  }
};
</script>

<style scoped>


.table-container {
  width: 60%;
  transform: scale(1); /* 放大表格 */
  margin-left: auto;
  margin-right: auto;
}

.table-title {
  margin-bottom: 10px;
}

.el-table {
  text-align: center; /* 表格内容居中 */
}

.el-table th, .el-table td {
  text-align: center;
}

.header {
            position: absolute;
            top: 70px;
            right: 70px;
        }
</style>
