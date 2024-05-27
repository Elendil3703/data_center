<template>
  <div id="app">
    <keep-alive>
    <router-view v-if="$route.meta.keepAlive"/> <!-- 这里渲染匹配的路由视图 -->
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"/>


  </div>
</template>

<script>


export default {
  name: 'App',
  
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
