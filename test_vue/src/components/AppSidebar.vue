<template>
  <el-row class="tac">
  <el-col>
    <el-menu
      :default-active="$route.path"
      class="el-menu-vertical-demo"
      @select="handleSelect">
      <el-menu-item index="/route-one">
        <i class="el-icon-view"></i>
          <span slot="title">数据可视化</span>
      </el-menu-item>
      <el-menu-item index="/route-two">
        <i class="el-icon-search"></i>
          <span slot="title">数据查询</span>
      </el-menu-item>
      <el-menu-item index="/route-three" >
        <i class="el-icon-help"></i>
          <span slot="title">分组&连接查询</span>
      </el-menu-item>
      <el-menu-item index="/route-four">
        <i class="el-icon-tickets"></i>
          <span slot="title">API管理</span>
      </el-menu-item>
      <el-menu-item index="/route-five" >
        <i class="el-icon-circle-check"></i>
          <span slot="title">操纵统一数据库</span>
      </el-menu-item>
      <el-menu-item index="/route-six" >
        <i class="el-icon-coin"></i>
          <span slot="title">权限管理</span>
      </el-menu-item>
      <el-menu-item index="5" class="menu-spacer" disabled></el-menu-item> 
    </el-menu>
  </el-col>
  
</el-row>

</template>

<script>
export default {
  data(){
    return{
    };
  },
  methods: {
  handleSelect(index) {
    //console.log(`route: ${$route.path}`);
    let path;
    // 根据选择的菜单项的 index 来确定路由路径
    switch (index) {
      case '/route-one':
        path = '/route-one';
        break;
      case '/route-two':
        path = '/route-two';
        break;
      case '/route-three':
        path = '/route-three';
        break;
      case '/route-four':
        path = '/route-four';
        break;
      case '/route-five':
        path = '/route-five';
        break;
      case '/route-six':
        path = '/route-six';
        break;
      default:
        // 如果没有匹配的index，可以设置一个默认的路由或者不做任何操作
        console.log('No valid index provided');
        return; // 直接返回，不进行导航
    }

    // 检查即将导航的路由是否已经是当前激活的路由
    if (this.$route.path !== path) {
      this.$router.push(path).catch(err => {
        if (err.name !== 'NavigationDuplicated' && !err.message.includes('Avoided redundant navigation')) {
          console.error(err); // 只处理非重复导航错误
        }
      });
    } else {
      console.log('Already on the target route:', path); // 如果已经在该路由上，可以选择记录日志或者什么都不做
    }
  },
  


  },
}
</script>

<style scoped>
.tac {
  width: 160px; /* 根据需要调整宽度 */
  height: calc(100vh - 50px); /* 减去顶部的偏移量 */
  position: fixed; /* 固定位置，不随页面滚动 */
  left: 0; /* 紧贴左侧 */
  top: 50px; /* 设置顶部偏移，留出空间给顶栏 */
  overflow-y: auto; /* 如果内容过多，允许滚动 */
}
.menu-spacer {
  height: 300px; /* 设置空白菜单项的高度 */
  pointer-events: none; /* 保持不可点击 */
  opacity: 0; /* 使其透明，不显示任何内容 */
}
</style>


