<template>
    <div class="create-container">
        <div class="search-container">
        <label class="search-label">数据表名称：</label>
        <el-input
            placeholder="数据表名称"
            v-model="name"
            style="width: 300px;"
            >
          </el-input>
        </div>
    <div class="radio-container"> 
        <el-radio v-model="permission" :label="0">共享表</el-radio>  
        <el-radio v-model="permission" :label="1">非共享表</el-radio>
    </div>
    <div class="param-container">
      <div v-for="(param, index) in fields" :key="index" class="param-field">
        <label class="param-label">新增列名称：</label>
        <el-input
          v-model="param.name"
          placeholder="新增列名称"
          style="width: 200px;"
        >
        </el-input>
        <label class="param-label" style="margin-left: 10px;">数据类型：</label>
        <el-select v-model="param.type" placeholder="请选择数据类型" style="width: 150px;">
          <el-option label="int" value="int"></el-option>
          <el-option label="date" value="date"></el-option>
          <el-option label="float" value="float"></el-option>
          <el-option label="double" value="double"></el-option>
          <el-option label="char" value="char"></el-option>
        </el-select>
        <el-checkbox v-model="param.primaryKey" style="margin-left: 10px;">设为主键</el-checkbox>
        <el-button type="danger" icon="el-icon-delete" @click="removeParam(index)" style="margin-left: 10px;"></el-button>
      </div>
      <el-button type="primary" icon="el-icon-plus" @click="addParam">新增参数</el-button>
      <div class="button-group">
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
    </div>
    <el-button class="return-button" type="primary" @click="emitGoBack">返回</el-button>
    </div>
</template>
<script>
export default{
    data () {
      return {
        name:'',
        permission: 1,
        fields: [
        { name: '', type: '', primaryKey: false }
      ],
      };
    },
    methods:{
    addParam() {
      this.fields.push({ name: '', type: '', primaryKey: false });
    },
    removeParam(index) {
      this.fields.splice(index, 1);
    },
    submit() {
      const payload = {
        name: this.name,
        permission: this.permission,
        fields: this.fields
      };
      console.log('Payload:', payload);
      this.$axios.post('/modify_database/create_table', payload)
        .then(response => {
          console.log('数据提交成功:', response.data);
          this.$message.success('数据提交成功');
        })
        .catch(error => {
          console.error('数据提交失败:', error);
          this.$message.error('数据提交失败');
        });
    },
    emitGoBack() {
      this.$router.push({ name: 'SqlMainpage' });
    }
    }
};

</script>
<style scoped>
.search-label {
  margin-right: 10px; /* 调整间距以适应布局 */
  white-space: nowrap;
  margin-top:10px;
}
.radio-container .el-radio {
  display: flex;
  margin-bottom: 10px; /* 设置单选按钮之间的间距 */ /* 设置单选按钮之间的间距 */
}
.radio-container{
    margin-top:50px;
    margin-left:540px;
    display: flex;
  flex-direction: column;
}
.param-container {
  margin-top: 20px;
  margin-left:100px;
  display: flex;
  flex-direction: column; /* 垂直排列子元素 */
  justify-content: center; /* 垂直居中对齐 */
  align-items: center; /* 水平居中对齐 */
}

.param-field {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.param-label {
  margin-right: 10px;
  white-space: nowrap;
}

.button-group {
  margin-top: 20px;
}

.return-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
}
</style>

