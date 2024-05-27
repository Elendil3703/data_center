<template>
  <div>
    <el-button type="primary" @click="createTable">创建数据表</el-button>
    <div class="table-container">
      <h2 class="table-title">所有数据表</h2>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="name" label="表格名称" width="180"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-link type="primary" @click="editRow(scope.row.name)">编辑</el-link>
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
        { name: '2016-05-02'},
        { name: '2016-05-04'},
        { name: '2016-05-01'},
        { name: '2016-05-03'}
      ]
    };
  },
  methods: {
    createTable() {
      console.log('创建数据表');
      this.$router.push({ name: 'CreateTable' });
    },
    editRow(name) {
      //console.log('编辑:', row);
      this.$router.push({ name: 'EditTable', params: { tableName: name } });
    },
    viewDetails(name) {
      //console.log('查看详情:', row);
      // 在组件方法中导航到 DataTable 路由
      this.$router.push({ name: 'DataTable', params: { tableName: name } });
    },
   deleteRow(name) {
    this.tableData = this.tableData.filter(table => table.name !== name);
    this.$axios.post('/api/delete-table', { name })
    .then(() => { // 不使用 response 变量
      this.$message.success('删除成功');
      // 删除成功后，刷新表格数据
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
      // 使用 this.$axios 发送请求
      this.$axios.get('/api/tables')
        .then(response => {
          // 将后端返回的表名数组转换为包含对象的数组
          this.tableData = response.data.map(name => ({ name }));
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
  width: 80%; /* 调整表格容器的宽度 */
  transform: scale(0.8); /* 缩放表格 */
  margin-top: 200px; /* 向下移动表格 */
  margin-left: auto;
  margin-right: auto;
}
.table-title {
  margin-bottom: 10px; /* 调整标题和表格之间的间距 */
}
</style>
