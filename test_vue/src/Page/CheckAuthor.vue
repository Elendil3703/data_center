<template>
    <div class="admin-view-container">
      <h2 class="admin-title">当前已有的业务系统管理员</h2>
      <el-table class="table-container" :data="paginatedData" style="width:800px;height:300px" border>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="password" label="密码"></el-table-column>
        <el-table-column prop="readable" label="可读表格"></el-table-column>
        <el-table-column prop="writable" label="可写表格"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-link type="primary" @click="editAdmin(scope.row)">编辑</el-link>
            <el-link type="danger" @click="deleteAdmin(scope.row)" style="margin-left: 10px;">删除</el-link>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page.sync="currentPage"
        @current-change="fetchAdminData"
        style="margin-top: 20px;">
      </el-pagination>
      <div class="param-field" style="margin-top: 20px;">
        <label class="param-label">搜索管理员：</label>
        <el-input
          v-model="Name"
          placeholder="请输入业务管理员姓名"
          style="width: 200px;"
        >
        </el-input>
        <el-button type="primary" @click="search" style="margin-left: 20px;">搜索</el-button>
        <div><el-button type="primary" @click="add" style="margin-top: 20px;">添加管理员</el-button></div>
      </div>
      <el-dialog
      title="搜索结果"
      :visible.sync="searchDialogVisible"
      width="600px">
      <el-table :data="searchResults" border>
        <el-table-column prop="adminID" label="编号"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="password" label="密码"></el-table-column>
        <el-table-column prop="readable" label="可读表格"></el-table-column>
        <el-table-column prop="writable" label="可写表格"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="searchDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
    </div>
  </template>

  <script>
export default {
  name: 'AdminView',
  data() {
    return {
      adminData: [
        { adminID: 1, name: 'Alice', password: 'password1', readable: 'table1, table2', writable: 'table1' },
        { adminID: 2, name: 'Bob', password: 'password2', readable: 'table2, table3', writable: 'table2' },
        { adminID: 3, name: 'Charlie', password: 'password3', readable: 'table1, table3', writable: 'table3' },
        { adminID: 4, name: 'David', password: 'password4', readable: 'table2', writable: 'table1, table3' },
        { adminID: 5, name: 'Eve', password: 'password5', readable: 'table1', writable: 'table2, table3,ffffffffffffffffffffffffffff' },
        { adminID: 1, name: 'Alice', password: 'password1', readable: 'table1, table2', writable: 'table1' },
        { adminID: 2, name: 'Bob', password: 'password2', readable: 'table2, table3', writable: 'table2' },
        { adminID: 3, name: 'Charlie', password: 'password3', readable: 'table1, table3', writable: 'table3' },
        { adminID: 4, name: 'Davd', password: 'password4', readable: 'table2', writable: 'table1, table3' },
        { adminID: 5, name: 'Eve', password: 'password5', readable: 'table1', writable: 'table2, table3,ffffffffffffffffffffffffffff' }
      ],
      pageSize: 5,
      currentPage: 1,
      Name: '',
      searchDialogVisible: false,
      searchResults: []
    };
  },
  computed: {
    paginatedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = this.currentPage * this.pageSize;
      return this.adminData.slice(start, end);
    },
    total() {
      return this.adminData.length;
    }
  },
  methods: {
    add(){
      this.$router.push({ name: 'AddAuthor' });
    },
    search(){
      this.searchResults = this.adminData.filter(admin => admin.name.includes(this.Name));
      this.searchDialogVisible = true;
    },
    fetchAdminData() {
      this.$axios.get('/permissions/show')
         .then(response => {
           this.adminData = response.data;
         })
         .catch(error => {
           console.error('获取管理员数据失败:', error);
         });
    },
    editAdmin(admin) {
      // 跳转到编辑管理员页面
      this.$router.push({ name: 'EditAuthor',query:{name: admin.name,
        adminID: admin.adminID,
        readable:admin.readable,
        writable:admin.writable,
        password:admin.password} });
    },
    deleteAdmin(admin) {
      this.$router.push({ name: 'DeleteAuthor', params: { username: admin.name } });
    }
  },
  created() {
    this.fetchAdminData();
  }
};
</script>

<style scoped>
.admin-view-container {
  padding: 20px;
}

.el-pagination {
  display: flex;
  justify-content: center;
}
.admin-title {
  text-align: center;
  margin-bottom: 20px; /* 添加一些底部间距使表格和标题分开 */
}

.table-container {
  margin: 0 auto; /* 使表格容器居中 */
}
</style>
