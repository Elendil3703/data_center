<template>
    <div class="edit-admin-container">
      <h2>编辑管理员权限</h2>
      <el-table class="table" :data="adminDataArray" style="width: 760px; margin-bottom: 20px;" border>
        <el-table-column prop="adminID" label="编号"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="password" label="密码"></el-table-column>
        <el-table-column prop="readable" label="可读表格"></el-table-column>
        <el-table-column prop="writable" label="可写表格"></el-table-column>
      </el-table>
      <div class="section_1">
        <h3>修改权限</h3>
        <el-input v-model="editForm.readable" placeholder="可读表格（用逗号分隔）" class="input-field" style="width: 300px; margin-bottom: 10px;"></el-input>
        <el-input v-model="editForm.writable" placeholder="可写表格（用逗号分隔）" class="input-field" style="width: 300px;"></el-input>
        <h3>验证身份权限</h3>
        <el-input v-model="authForm.username" placeholder="用户名" class="input-field" style="width: 300px; margin-bottom: 10px;"></el-input>
        <el-input v-model="authForm.password" placeholder="密码" class="input-field" style="width: 300px;"></el-input>
      </div>
      <div class="button-group">
        <el-button type="primary" @click="confirm">确认</el-button>
        <el-button @click="clearFields">取消</el-button>
      </div>
    </div>
  </template>

  <script>
export default {
  name: 'EditAdmin',
  data() {
    return {
      adminData: this.$route.query,
      editForm: {
        readable: '',
        writable: ''
      },
      authForm: {
        username: '',
        password: ''
      }
    };
  },
  computed: {
    adminDataArray() {
      return Array.isArray(this.adminData) ? this.adminData : [this.adminData];
    }
  },
  methods: {
    confirm() {
      // 实现确认逻辑，例如发送请求到后端
      const payload = {
        name:this.adminDataArray.name,
        password:this.adminDataArray.password,
        readable:this.editForm.readable,
        writable:this.editForm.writable,
        adminName:this.authForm.username,
        adminPassword:this.authForm.password
      };
      console.log('Payload:', payload);
      this.$axios.post('/permissions/update', payload)
        .then(response => {
          console.log('修改成功:', response.data);
          this.$message.success('修改成功');
        })
        .catch(error => {
          console.error('修改失败:', error);
          this.$message.error('修改失败');
        });
    },
    clearFields() {
      this.editForm.readable = '';
      this.editForm.writable = '';
      this.authForm.username = '';
      this.authForm.password = '';
    }
  }
};
</script>

<style scoped>
.edit-admin-container {
  padding: 20px;
}

.section_1{
  margin: 0 auto;
  text-align: center;
}
.input-field {
  width: 300px;
  display: block;
  margin: 10px auto; /* 自动水平居中并增加间距 */
}
.button-group {
  gap: 10px;
  margin: 0 auto;
}

.el-input {
  display: block;
}
.table {
  margin: 0 auto; /* 使表格容器居中 */
}
</style>
