<template>
  <div class="detail-container">
    <h2>表格名称：{{ tableName }}</h2>
    <div class="table-wrapper">
      <div class="table-container">
        <el-table
          :data="tableData"
          style="width: 800px; margin-top: 20px;"
          :height="400"
          border
        >
          <el-table-column
            v-for="column in columns"
            :key="column.COLUMN_NAME"
            :prop="column.COLUMN_NAME"
            :label="column.COLUMN_NAME"
            width="column.width"
            align="center"
            :show-overflow-tooltip="true"
          >
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="button-group">
      <el-button type="primary" @click="editTable">编辑</el-button>
      <el-button @click="goBack">返回</el-button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    tableName: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      tableData: [
        { id: 1, name: 'Alice看过i机柜恶杰润份额日u房内', age: 20 },
        { id: 2, name: 'Bob', age: 30 },
        { id: 3, name: 'Charlie', age: 35 },
        { id: 1, name: 'Alice', age: 20 },
        { id: 2, name: 'Bob', age: 30 },
        { id: 3, name: 'Charlie', age: 35 },
        { id: 1, name: 'Alice', age: 20 },
        { id: 2, name: 'Bob', age: 30 },
        { id: 3, name: 'Charlie', age: 35 },
        { id: 1, name: 'Alice', age: 20 },
        { id: 2, name: 'Bob', age: 30 },
        { id: 3, name: 'Charlie', age: 35 }
      ],
      columns: [
        { COLUMN_NAME: 'id' },
        { COLUMN_NAME: 'name' },
        { COLUMN_NAME: 'age' },
        { COLUMN_NAME: 's' },
        { COLUMN_NAME: 'n' },
        { COLUMN_NAME: 'a' },
        { COLUMN_NAME: 'na' },
        { COLUMN_NAME: 'ag' },
        { COLUMN_NAME: 'sx' },
        { COLUMN_NAME: 'nx' },
        { COLUMN_NAME: 'ax' }
      ]
    };
  },
  methods: {
    fetchColumnData() {
      // 使用 this.$axios 发送请求以获取列信息
      this.$axios.get('/modify_database/table_info')
        .then(response => {
          // 设置 columns 数据
          this.columns = response.data;
          // 然后获取表数据
          this.fetchTableData();
        })
        .catch(error => {
          console.error('获取列信息失败:', error);
        });
    },
    fetchTableData() {
      // 使用 this.$axios 发送请求以获取表格数据
      this.$axios.get('/modify_database/table_data')
        .then(response => {
          // 设置 tableData 数据
          this.tableData = response.data;
        })
        .catch(error => {
          console.error('获取表格数据失败:', error);
        });
    },
    editTable() {
      // 处理编辑操作
      this.$router.push({ name: 'EditTable', params: { tableName: this.tableName } });
    },
    goBack() {
      // 处理返回操作
      this.$router.push({ name: 'SqlMainpage' });
    }
  },
  created() {
    this.fetchColumnData();
  }
};
</script>

<style scoped>
.detail-container {
  padding: 20px;
}

.table-wrapper {
  overflow: auto; /* 添加水平滚动条 */
  width: 100%; /* 确保容器不会超过父容器的宽度 */
  display: flex;
  justify-content: center; /* 使表格居中 */
}

.table-container {
  width: fit-content; /* 让宽度根据内容调整 */
}

.button-group {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  max-width: 800px;
  margin: 20px auto;
}

.el-table {
  margin-top: 20px;
}

.el-button {
  margin-right: 10px;
}
</style>
