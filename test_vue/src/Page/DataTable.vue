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
      <el-button @click="showFilterDialog">筛选</el-button>
      <el-button @click="cancelFilter">取消筛选</el-button>
      <el-button @click="goBack">返回</el-button>
    </div>
    <el-dialog title="筛选表数据" :visible.sync="filterDialogVisible">
      <el-form :model="filterForm">
        <el-form-item label="列名称">
          <el-input v-model="filterForm.columnName"></el-input>
        </el-form-item>
        <el-form-item label="最小值">
          <el-input v-model="filterForm.minValue"></el-input>
        </el-form-item>
        <el-form-item label="最大值">
          <el-input v-model="filterForm.maxValue"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="filterDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="applyFilter">确认</el-button>
      </div>
    </el-dialog>
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
      ],
      filterDialogVisible: false,
      filterForm: {
        columnName: '',
        minValue: '',
        maxValue: ''
      }
    };
  },
  methods: {
    fetchColumnData() {
      this.$axios.get(`/modify_database/table_info?tableName=${this.tableName}`)
        .then(response => {
          this.columns = response.data;
          this.fetchTableData();
        })
        .catch(error => {
          console.error('获取列信息失败:', error);
        });
    },
    fetchTableData() {
      this.$axios.get(`/modify_database/table_data?tableName=${this.tableName}`)
        .then(response => {
          this.tableData = response.data;
        })
        .catch(error => {
          console.error('获取表格数据失败:', error);
        });
    },
    editTable() {
      this.$router.push({ name: 'EditTable', params: { tableName: this.tableName } });
    },
    goBack() {
      this.$router.push({ name: 'SqlMainpage' });
    },
    showFilterDialog() {
      this.filterDialogVisible = true;
    },
    applyFilter() {
      const { columnName, minValue, maxValue } = this.filterForm;
      this.$axios.post(`/modify_database/filter`, {
        params: {
          tableName: this.tableName,
          columnName,
          minValue,
          maxValue
        }
      })
      .then(response => {
        this.tableData = response.data;
        this.filterDialogVisible = false;
      })
      .catch(error => {
        console.error('筛选表格数据失败:', error);
      });
    },
    cancelFilter() {
      this.fetchTableData();
    }
  },
  created() {
    this.fetchColumnData();
  }
};
</script>

<style scoped>
.table-wrapper {
  overflow: auto;
  width: 100%;
  display: flex;
  justify-content: center;
  max-height: calc(100vh - 100px);
}

.table-container {
  width: fit-content;
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
.detail-container {
  height: calc(100vh - 40px);
  padding: 20px;
  box-sizing: border-box;
}
</style>
