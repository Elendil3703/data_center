<template>
  <div class="edit-table-container">
    <h2>表格名称：{{ tableName }}</h2>
    <div class="table-wrapper">
      <div class="table-container">
        <el-table
          :data="tableData"
          style="width: 800px;"
          :height="400"
          @cell-click="handleCellClick"
          border
        >
          <el-table-column
            v-for="column in columns"
            :key="column.COLUMN_NAME"
            :prop="column.COLUMN_NAME"
            :label="column.COLUMN_NAME"
            align="center"
            :width="column.width"
            :show-overflow-tooltip="true"
          >
          <template #header>
            <div class="header-container">
    <span class="column-name">{{ column.COLUMN_NAME }}</span>
            </div>
  </template>
            <template slot-scope="scope">
              <el-input
                v-if="editingCell.row === scope.row && editingCell.column === column.COLUMN_NAME"
                v-model="scope.row[column.COLUMN_NAME]"
                @blur="editingCell = {}"
                size="mini"
              />
              <span v-else>{{ scope.row[column.COLUMN_NAME] }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="button-group">
      <el-button type="primary" @click="saveChanges">保存</el-button>
      <el-button @click="goBack">返回</el-button>
      <el-button type="primary" @click="showAddFieldDialog">添加字段</el-button>
      <el-button type="primary" @click="showdeletedialog">删除字段</el-button>
    </div>
    <el-dialog :visible.sync="addFieldDialogVisible" title="添加字段">
      <el-form :model="newField">
        <el-form-item label="字段名称">
          <el-input v-model="newField.COLUMN_NAME"></el-input>
        </el-form-item>
        <el-form-item label="字段类型">
          <el-select v-model="newField.type" placeholder="请选择数据类型" style="width: 150px;">
          <el-option label="int" value="int"></el-option>
          <el-option label="date" value="date"></el-option>
          <el-option label="float" value="float"></el-option>
          <el-option label="double" value="double"></el-option>
          <el-option label="char" value="char"></el-option>
        </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handlecancel">取消</el-button>
        <el-button type="primary" @click="addField">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="deleteFieldDialogVisible" title="删除字段">
      <el-form>
        <el-form-item label="字段名称">
          <el-input v-model="deleteFieldNAME"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handlecancel_1">取消</el-button>
        <el-button type="primary" @click="addField_1">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'EditTable',
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
      editingCell: {},
      loading: true,
      addFieldDialogVisible: false,
      deleteFieldDialogVisible:false,
    newField: {
      COLUMN_NAME: '',
      type: ''
    },
    deleteFieldNAME:'',
    };
  },
  methods: {
    fetchColumnData() {
      // 使用静态测试数据填充表格内容
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
    showAddFieldDialog() {
    this.addFieldDialogVisible = true;
  },
  showdeletedialog() {
    this.deleteFieldDialogVisible = true;
  },
  handlecancel() {
    this.newField.COLUMN_NAME = '';
    this.newField.type = '';
    this.addFieldDialogVisible = false;
  },
  handlecancel_1() {
    this.deleteFieldNAME = '';
    this.deleteFieldDialogVisible = false;
  },
  addField() {
    if (this.newField.COLUMN_NAME && this.newField.type) {
      // 添加新字段到列
      this.columns.push({ COLUMN_NAME: this.newField.COLUMN_NAME });
      
      // 添加新字段到每一行数据
      this.tableData.forEach(row => {
        this.$set(row, this.newField.COLUMN_NAME, '');
      });

      // 清空新字段表单
      this.newField.COLUMN_NAME = '';
      this.newField.type = '';

      // 保持对话框可见
      this.addFieldDialogVisible = true;
    } else {
      this.$message.error('请填写完整字段信息');
    }
    const payload = {
        tableName: this.tableName,
        columnName: this.newField.COLUMN_NAME,
        columnType: this.newField.type
      };
      console.log('Payload:', payload);
      this.$axios.post('/modify_database/add_field', payload)
        .then(response => {
          console.log('添加成功:', response.data);
          this.$message.success('添加成功');
        })
        .catch(error => {
          console.error('添加失败:', error);
          this.$message.error('添加失败');
        });
  },
    handleCellClick(row, column) {
      this.editingCell = { row, column: column.property };
    },
    saveChanges() {
      // 模拟保存逻辑
      console.log('保存数据:', this.tableData);
      const payload = {
        name: this.tableName,
        fields: this.tableData
      };
      console.log('Payload:', payload);
      this.$axios.post('/api/your-endpoint', payload)
        .then(response => {
          console.log('保存成功:', response.data);
          this.$message.success('保存成功');
        })
        .catch(error => {
          console.error('保存失败:', error);
          this.$message.error('保存失败');
        });
    },
    goBack() {
      this.$router.push({ name: 'SqlMainpage' });
    },
    addField_1(){
        if(this.deleteFieldNAME){
            const columnIndex = this.columns.findIndex(column => column.COLUMN_NAME === this.deleteFieldNAME);
    
    // 如果找到列，删除列
    if (columnIndex !== -1) {
      // 获取要删除的列名
      const columnToDelete = this.columns[columnIndex].COLUMN_NAME;

      // 从 columns 数组中删除指定列
      this.columns.splice(columnIndex, 1);

      // 从 tableData 中删除相应字段
      this.tableData = this.tableData.map(row => {
        // 创建一个新的对象，不包括要删除的字段
        const newRow = { ...row };
        delete newRow[columnToDelete];
        return newRow;
      });

    
    } else {
      this.$message.error('未找到指定的列');
    }
        }
    else {
      this.$message.error('请填写完整字段信息');
    }
    const payload = {
        tableName: this.tableNname,
        columnName: this.deleteFieldNAME
      };
    // 提示删除成功
    this.$axios.post('/api/delete-table', payload)
    .then(() => { // 不使用 response 变量
      this.$message.success('删除成功');
    })
    .catch(error => {
      if (error.response && error.response.status === 400) {
        this.$message.error('表格或字段不存在');
      } else {
        this.$message.error('删除失败');
      }
      console.error('删除失败:', error);
    });

    }
  },
  created() {
    this.fetchColumnData();
  }
};
</script>

<style scoped>
.edit-table-container {
  padding: 20px;
  text-align: center;
}

.table-wrapper {
  display: flex;
  justify-content: center;
  overflow-x: auto; /* 添加水平滚动条 */
  overflow-y: auto;
  width: 100%;
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
.header-container {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.column-name {
  flex: 1;
  text-align: center;
  line-height: 30px; /* 设置与按钮高度一致的行高 */
  height: 30px; /* 确保高度与按钮一致 */
}

.delete-button {
  position: absolute;
  right: 10px;
}

</style>