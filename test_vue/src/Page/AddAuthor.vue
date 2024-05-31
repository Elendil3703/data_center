<template>
    <div class="edit-admin-container">
      <div class="section_1">
        <h3>业务系统管理员信息</h3>
        <el-input v-model="name" placeholder="业务管理员姓名" class="input-field" style="width: 300px; margin-bottom: 10px;"></el-input>
        <el-input v-model="password" placeholder="业务管理员密码" class="input-field" style="width: 300px;"></el-input>
        <h3>业务系统管理员权限</h3>
        <el-input v-model="readable" placeholder="可读表格（用逗号分隔）" class="input-field" style="width: 300px; margin-bottom: 10px;"></el-input>
        <el-input v-model="writable" placeholder="可写表格（用逗号分隔）请勿写入共享表格的表格序号" class="input-field" style="width: 370px;"></el-input>
      </div>
      <div class="button-group">
        <el-button type="primary" @click="confirm">确认</el-button>
        <el-button @click="clearFields">返回</el-button>
      </div>
    </div>
  </template>

  <script>
export default {
  name: 'AddAdmin',
  data() {
    return {
        readable: '',
        writable: '',
        name: '',
        password: ''
    };
  },
  methods: {
    confirm() {
      // 实现确认逻辑，例如发送请求到后端
      const params = new URLSearchParams({
    name:this.name,
    password:this.password,
    readable:this.readable,
    writable:this.writable,
  }).toString();
      this.$axios.post(`/permissions/set?${params}`)
        .then(response => {
          console.log('添加成功:', response.data);
          this.$message.success('添加成功');
        })
        .catch(error => {
          console.error('添加失败:', error);
          this.$message.error('添加失败');
        });
    },
    clearFields() {
      this.$router.push({ name: 'CheckAuthor' });
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
