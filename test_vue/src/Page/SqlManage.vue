<!-- src/components/MainLayout.vue -->
<template>
  <div class="main-layout">
    <AppSidebar></AppSidebar>
    <div class="main-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import AppSidebar from '@/components/AppSidebar.vue';

export default {
  components: {
    AppSidebar
  }
};
</script>

<style scoped>
.main-layout {
  display: flex;
}

.main-content {
  flex: 1;
  padding: 20px;
}
</style>
