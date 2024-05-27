<template>
  <div class="admin-dashboard">
    <AppSidebar></AppSidebar> <!-- 添加 Sidebar 组件 -->
    <div style="width:1440px;">
    <el-row :gutter="100" class="input-row" style="margin-left: 150px;">
      <el-col :span="7" style="width: 390px;">
        <div class="search-container">
        <label class="search-label">精准查询</label>
          <el-input
            placeholder="查询内容需要与数据完全匹配"
            v-model="input1"
            style="width: 100%;"
            >
          </el-input>
        </div> 
      </el-col>
      <el-col :span="7" style="width: 390px;">
        <div class="search-container1">
        <label class="search-label1">模糊查询</label>
          <el-input
            placeholder="查询大致相符的数据记录"
            v-model="input2"
            style="width: 100%;"
            >
          </el-input>
        </div>
      </el-col>
      <el-col :span="7" style="width: 390px;">
        <div class="search-container2">
        <label class="search-label2">筛选分类</label>
          <el-select v-model="value" placeholder="请选择">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
        </div>
      </el-col>
      <el-col :span="3">
        <el-button type="primary" icon="el-icon-search" style="height: 40px;">查询</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="100" class="input-row1" style="margin-left: 150px;">
      <el-col :span="7" style="width: 390px;">
        <div class="search-container">
        <label class="search-label">编号范围</label>
          <el-input
            prefix-icon="el-icon-set-up"
            placeholder="最小编号-最大编号"
            v-model="input3"
            style="width: 100%;"
            >
          </el-input>
        </div> 
      </el-col>
      <el-col :span="7" style="width: 390px;">
        <div class="search-container1">
        <label class="search-label1">创建时间</label>
          <el-input
            prefix-icon="el-icon-date"
            placeholder="开始日期-结束日期"
            v-model="input4"
            style="width: 100%;"
            >
          </el-input>
        </div>
      </el-col>
      <el-col :span="7" style="width: 390px;">
        <div class="search-container2">
        <label class="search-label2">筛选分类</label>
          <el-select v-model="value_2" placeholder="请选择">
    <el-option
      v-for="item in options_2"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
        </div>
      </el-col>
      <el-col :span="3">
        <el-button type="primary" icon="el-icon-refresh-left" style="height: 40px;">重置</el-button>
      </el-col>
    </el-row>
    <div class="divider"></div>
    </div>
  </div>
</template>

<script>
import AppSidebar from '@/components/AppSidebar.vue';
export default {
  components: {
    AppSidebar
  },
  data() {
    return {
      searchQuery: '',
      selectedFilter: '',
      items: [],
      currentPage: 1,
      pageSize: 10,
      activeIndex: null,
      input1:null,
      input2:null,
      input3:null,
      input4:null,
      options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        value: '',
        value_2: '',
        options_2: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
    };
  },
  methods: {
    search() {
      // 实现搜索逻辑
    },
    nextPage() {
      // 翻页逻辑
      this.currentPage++;
    },
    previousPage() {
      // 翻页逻辑
      if (this.currentPage > 1) this.currentPage--;
    },
    updateActiveIndex(index) {
      this.activeIndex = index;
    }
  },
  created() {
    // 初始化获取数据的逻辑
  },
};
</script>

<style scoped>
.input-row {
  margin-top: 130px; /* 向下移动位置 */
  margin-bottom: 10px;
}
.search-container {
  display: flex;
}
.search-label {
  margin-right: 10px; /* 调整间距以适应布局 */
  white-space: nowrap;
  margin-top:10px;
}
.search-container1 {
  display: flex;
}
.search-label1 {
  margin-right: 10px; /* 调整间距以适应布局 */
  white-space: nowrap;
  margin-top:10px;
}
.search-container2 {
  display: flex;
}
.search-label2 {
  margin-right: 10px; /* 调整间距以适应布局 */
  white-space: nowrap;
  margin-top:10px;
}
.divider {
  height: 1px;
  background-color: #dcdcdc; /* 分界线颜色 */
  margin: 10px 0; /* 调整分界线上下间距 */
}
</style>

