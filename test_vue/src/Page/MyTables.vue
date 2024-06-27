<template>
    <div class = "admin-dashboard">
      <AppSidebar></AppSidebar>
      <div>
        <h1>数据库表名列表</h1>
        <AppSidebar></AppSidebar>
        <!-- 输入框，用于输入数据库名 -->
        <input v-model="databaseName" placeholder="输入数据库名" />
        <!-- 按钮，点击后触发fetchTables方法 -->
        <button @click="fetchTables">获取表名</button>
        <!-- 列表，显示获取到的表名 -->
        <ul>
          <li v-for="table in tables" :key="table.id">
            {{ table.name }} - 部门ID: {{ table.department_id }} - 薪水: {{ table.salary }} - 年龄: {{ table.age }}
          </li>
        </ul>
      </div>
    </div>  
  </template>
  
  <script>
  
  import AppSidebar from '@/components/AppSidebar.vue';
  import axios from 'axios';
  
  export default {
    name:'MyTables',
    components: {
      AppSidebar
    },
    data() { 
      return {
        databaseName: '', // 绑定输入框的数据库名
        tables: [] // 存储从API获取的表名数据
      };
    },
    methods: {
      // 异步方法，用于获取表名数据
      async fetchTables() {
        // 检查数据库名是否为空
        if (this.databaseName) {
          try {
            // 发送GET请求到API，并传递数据库名作为参数
            const response = await axios.get('https://example.com/api/doris/tables', {
              params: {
                databaseName: this.databaseName
              }
            });
            // 将响应数据存储在tables中
            this.tables = response.data;
          } catch (error) {
            // 捕获错误并输出到控制台
            console.error('Error fetching tables:', error);
          }
        } else {
          // 如果数据库名为空，弹出提示
          alert('请输入数据库名');
        }
      }
    }
  };
  </script>
  
  <style scoped>
  /* 您可以在此处添加样式 */
  </style>
  