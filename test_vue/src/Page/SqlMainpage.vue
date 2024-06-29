<template>
  <div>
    <div class="header">
      <el-button type="primary" @click="createTable" class="create-button">创建数据表</el-button>
    </div>
    <div class="table-container">
      <h2 class="table-title">所有数据表</h2>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="表格编号" width="180"></el-table-column>
        <el-table-column prop="name" label="表格名称" width="180"></el-table-column>
        <el-table-column prop="permission" label="共享状态" width="180" :formatter="formatPermission"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-link type="primary" @click="editRow(scope.row.name, scope.row.permission)" class="action-link">编辑</el-link>
            <el-link type="primary" @click="viewDetails(scope.row.name)" class="action-link">查看详情</el-link>
            <el-link type="primary" @click="deleteRow(scope.row.name)" class="action-link">删除</el-link>
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
        { id: 1, name: '2016-05-02', permission: true },
        { id: 2, name: '2016-05-04', permission: false },
        { id: 3, name: '2016-05-01', permission: true },
        { id: 4, name: '2016-05-03', permission: false }
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
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css');

body {
  background: linear-gradient(to right, #ff7e5f, #feb47b);
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
  font-family: 'Arial', sans-serif;
}

.header {
  position: absolute;
  top: 20px; /* 调整这里的上边距 */
  right: 70px;
}

.create-button {
  background-color: #28a745;
  border-color: #28a745;
  color: white;
  transition: all 0.3s ease;
  border-radius: 25px;
}

.create-button:hover {
  background-color: #218838;
  border-color: #218838;
}

.table-container {
  max-width: 800px;
  margin: 10px auto;
  padding: 30px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.table-title {
  text-align: center;
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

.el-table th {
  background-color: #f5f5f5;
  color: #333;
  font-weight: bold;
}

.el-table th, .el-table td {
  text-align: center;
}

.el-link {
  margin: 0 5px;
  color: #007BFF;
  transition: color 0.3s ease;
}

.el-link:hover {
  color: #0056b3;
}

.action-link {
  margin-left: 10px;
}
</style>
