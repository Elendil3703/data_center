<template>
    <div class="edit-admin-container">
        <h2>确定要删除业务系统管理员{{username}}吗？</h2>
      <div class="section_1">
        <h3>验证身份权限</h3>
        <el-input v-model="adminName" placeholder="请输入数据中台管理员用户名" class="input-field" style="width: 300px; margin-bottom: 10px;"></el-input>
        <el-input v-model="adminPassword" placeholder="请输入数据中台管理员密码" class="input-field" style="width: 300px;"></el-input>
      </div>
      <div class="button-group">
        <el-button type="danger" @click="confirm">确认删除</el-button>
        <el-button @click="clearFields">取消</el-button>
      </div>
    </div>
  </template>

  <script>
export default {
  name: 'DeleteAdmin',
  data() {
    return {
        adminName: '',
        adminPassword: ''
    };
  },
  props: {
    username: {
      type: String,
      required: true
    }
  },
  methods: {
    confirm() {
      // 实现确认逻辑，例如发送请求到后端
      const payload = {
        name:this.username,
        adminName:this.adminName,
        adminPassword:this.adminPassword
      };
      console.log('Payload:', payload);
      this.$axios.post('/permissions/delete', payload)
        .then(response => {
          console.log('删除成功:', response.data);
          this.$message.success('删除成功');
        })
        .catch(error => {
          console.error('删除失败:', error);
          this.$message.error('删除失败');
        });
    },
    clearFields() {
      this.adminName = '';
      this.adminPassword = '';
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

</style>
