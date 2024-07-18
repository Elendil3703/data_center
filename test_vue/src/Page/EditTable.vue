<template>
  <div class="edit-table-container">
    <h2>表格名称：{{ tableName }}</h2>
    <span v-if="!st">（共享）</span>
    <span v-else>（非共享）</span>
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
          <el-table-column
            label="操作"
            width="150"
            align="center"
          >
            <template slot-scope="scope">
              <el-button @click="deleteRow(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div class="button-group">
      <el-button type="primary" @click="saveChanges">保存</el-button>
      <el-button @click="goBack">返回</el-button>
      <el-button type="primary" @click="showChangeStateDialog">修改表状态</el-button>
      <el-button type="primary" @click="showAddFieldDialog">添加字段</el-button>
      <el-button type="primary" @click="showdeletedialog">删除字段</el-button>
    </div>
    <el-dialog :visible.sync="addFieldDialogVisible" title="添加字段">
      <el-form :model="newField">
        <el-form-item label="字段名称">
          <el-input v-model="newField.COLUMN_NAME"></el-input>
        </el-form-item>
        <el-form-item label="字段类型">
          <el-input v-model="newField.type" placeholder="请准确输入mysql数据类型"></el-input>
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
        <el-button type="primary" @click="deleteField">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog class="log" :visible.sync="changeStateDialogVisible" title="修改表状态">
      <el-form :model="tableState">
        <el-form-item label="表状态">
          <el-radio-group v-model="tableState.per">
            <el-radio :label="0">共享</el-radio>
            <el-radio :label="1">非共享</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelChangeState">取消</el-button>
        <el-button type="primary" @click="submitChangeState">确定</el-button>
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
    },
    state: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      tableData: [
        { id: 1, name: 'Alice', age: 20 },
        { id: 2, name: 'Bob', age: 30 },
        { id: 3, name: 'Charlie', age: 35 }
      ],
      columns: [
        { COLUMN_NAME: 'id', COLUMN_KEY: 'PRI' },
        { COLUMN_NAME: 'name' },
        { COLUMN_NAME: 'age' },
        { COLUMN_NAME: 'id', COLUMN_KEY: 'PRI' },
        { COLUMN_NAME: 'name' },
        { COLUMN_NAME: 'age' },
        { COLUMN_NAME: 'id', COLUMN_KEY: 'PRI' },
        { COLUMN_NAME: 'name' },
        { COLUMN_NAME: 'age' }
      ],
      editingCell: {},
      loading: true,
      addFieldDialogVisible: false,
      deleteFieldDialogVisible: false,
      changeStateDialogVisible: false,
      newField: {
        COLUMN_NAME: '',
        type: ''
      },
      deleteFieldNAME: '',
      tableState: {
        per: this.state ? 1 : 0
      },
      st: this.state ? 1 : 0
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
    showAddFieldDialog() {
      this.addFieldDialogVisible = true;
    },
    showdeletedialog() {
      this.deleteFieldDialogVisible = true;
    },
    showChangeStateDialog() {
      this.changeStateDialogVisible = true;
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
    cancelChangeState() {
      this.changeStateDialogVisible = false;
    },
    addField() {
      if (this.newField.COLUMN_NAME && this.newField.type) {
        this.columns.push({ COLUMN_NAME: this.newField.COLUMN_NAME });
        this.tableData.forEach(row => {
          this.$set(row, this.newField.COLUMN_NAME, '');
        });
        this.addFieldDialogVisible = false;
        const params = new URLSearchParams({
          tableName: this.tableName,
          columnName: this.newField.COLUMN_NAME,
          columnType: this.newField.type
        }).toString();
        this.$axios.post(`/modify_database/add_field?${params}`)
          .then(response => {
            console.log('添加成功:', response.data);
            this.$message.success('添加成功');
            this.newField.COLUMN_NAME = '';
            this.newField.type = '';
          })
          .catch(error => {
            console.error('添加失败:', error);
            this.$message.error('添加失败');
          });
      } else {
        this.$message.error('请填写完整字段信息');
      }
    },
    deleteField() {
      if (this.deleteFieldNAME) {
        const columnIndex = this.columns.findIndex(column => column.COLUMN_NAME === this.deleteFieldNAME);
        if (columnIndex !== -1) {
          const columnToDelete = this.columns[columnIndex].COLUMN_NAME;
          this.columns.splice(columnIndex, 1);
          this.tableData = this.tableData.map(row => {
            const newRow = { ...row };
            delete newRow[columnToDelete];
            return newRow;
          });
          this.deleteFieldDialogVisible = false;
          const params = new URLSearchParams({
            tableName: this.tableName,
            columnName: this.deleteFieldNAME
          }).toString();
          this.$axios.post(`/modify_database/remove_field?${params}`)
            .then(() => {
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
        } else {
          this.$message.error('未找到指定的列');
        }
      } else {
        this.$message.error('请填写完整字段信息');
      }
    },
    handleCellClick(row, column) {
      this.editingCell = { row, column: column.property };
    },
    saveChanges() {
      const payload = {
        name: this.tableName,
        fields: this.tableData
      };
      this.$axios.post('/modify_database/update_data', payload)
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
    submitChangeState() {
      const payload = {
        tableName: this.tableName,
        permission: this.tableState.per
      };
      this.$axios.post('/modify_database/change_state', payload)
        .then(response => {
          this.st = this.tableState.per;
          console.log('状态修改成功:', response.data);
          this.$message.success('状态修改成功');
          this.changeStateDialogVisible = false;
        })
        .catch(error => {
          console.error('状态修改失败:', error);
          this.$message.error('状态修改失败');
        });
    },
    deleteRow(row) {
      const primaryKeyColumn = this.columns.find(column => column.COLUMN_KEY === 'PRI');
      if (primaryKeyColumn) {
        const primaryKeyValue = row[primaryKeyColumn.COLUMN_NAME];
        const payload = {
          tableName: this.tableName,
          primaryKey: primaryKeyColumn.COLUMN_NAME,
          primaryKeyValue: primaryKeyValue
        };
        //this.tableData = this.tableData.filter(item => item[primaryKeyColumn.COLUMN_NAME] !== primaryKeyValue);
        this.$axios.post('/modify_database/delete_row', payload)
          .then(() => {
            this.tableData = this.tableData.filter(item => item[primaryKeyColumn.COLUMN_NAME] !== primaryKeyValue);
            this.$message.success('删除成功');
          })
          .catch(error => {
            console.error('删除失败:', error);
            this.$message.error('删除失败');
          });
      } else {
        this.$message.error('未找到主键列');
      }
    }
  },
  created() {
    this.fetchColumnData();
  }
};
</script>

<style scoped>
.table-wrapper {
  display: flex;
  justify-content: center;
  overflow-x: auto;
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
  line-height: 30px;
  height: 30px;
}

.delete-button {
  position: absolute;
  right: 10px;
}

.log {
  top: 100px;
}
</style>
