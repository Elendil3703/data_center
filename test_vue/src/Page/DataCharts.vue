<template>
  <div class="admin-dashboard">
    <AppSidebar></AppSidebar> <!-- 添加 Sidebar 组件 -->
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
      activeIndex: null
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
    },
    
  },
  created() {
    // 初始化获取数据的逻辑
  },
};
</script>



