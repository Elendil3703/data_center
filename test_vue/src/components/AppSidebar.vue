<template>
  <el-row class="tac">
    <el-col>
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical-demo"
        @select="handleSelect">
        <el-menu-item index="/route-one">
          <i class="el-icon-view"></i>
          <span>数据可视化</span>
        </el-menu-item>
        <el-menu-item index="/route-three">
          <i class="el-icon-help"></i>
          <span>分组&连接查询</span>
        </el-menu-item>
        <el-menu-item index="/route-five">
          <i class="el-icon-circle-check"></i>
          <span>操纵统一数据库</span>
        </el-menu-item>
        <el-menu-item index="/route-six">
          <i class="el-icon-coin"></i>
          <span>权限管理</span>
        </el-menu-item>
        <el-menu-item index="5" class="menu-spacer" disabled></el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      activeMenu: this.$route.path // 初始化时设置activeMenu为当前路径
    };
  },
  watch: {
    $route(to) {
      // 监听路由变化，更新activeMenu
      this.activeMenu = to.path;
    }
  },
  methods: {
    handleSelect(index) {
      if (this.$route.path !== index) {
        this.$router.push(index).catch(err => {
          if (err.name !== 'NavigationDuplicated' && !err.message.includes('Avoided redundant navigation')) {
            console.error(err); // 只处理非重复导航错误
          }
        });
      } else {
        console.log('Already on the target route:', index);
      }
    },
  },
  created() {
    // 初始化时设置activeMenu为当前路径
    this.activeMenu = this.$route.path;
  }
};
</script>

<style scoped>
.tac {
  width: 200px; /* 根据需要调整宽度 */
  height: calc(100vh - 50px); /* 减去顶部的偏移量 */
  position: fixed; /* 固定位置，不随页面滚动 */
  left: 0; /* 紧贴左侧 */
  top: 50px; /* 设置顶部偏移，留出空间给顶栏 */
  overflow-y: auto; /* 如果内容过多，允许滚动 */
}
.menu-spacer {
  height: 250px; /* 设置空白菜单项的高度 */
  pointer-events: none; /* 保持不可点击 */
  opacity: 0; /* 使其透明，不显示任何内容 */
}
</style>
