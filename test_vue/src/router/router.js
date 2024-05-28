import Vue from 'vue';
import VueRouter from 'vue-router';
import DataQuery from '@/Page/DataQuery.vue'; // 确保路径正确
import GroupSearch from '@/Page/GroupSearch.vue';
import DataCharts from '@/Page/DataCharts.vue';
import ApiManage from '@/Page/ApiManage.vue';
import SqlManage from '@/Page/SqlManage.vue';
import SqlMainpage from '@/Page/SqlMainpage.vue';
import DataTable from '@/Page/DataTable.vue';
import CreateTable from '@/Page/CreateTable.vue';
import AuthorManage from '@/Page/AuthorManage.vue';
import CheckAuthor from '@/Page/CheckAuthor.vue';
import EditAuthor from '@/Page/EditAuthor.vue';
import AddAuthor from '@/Page/AddAuthor.vue';
import DeleteAuthor from '@/Page/DeleteAuthor.vue';
import EditTable from '@/Page/EditTable.vue';
import LoginRoot from '@/Page/LoginRoot.vue';
import store from '@/store';
Vue.use(VueRouter);

const routes = [
  {
    path: '/home',
    component: DataQuery,
  },
  {
    path: '/',
    component:LoginRoot
  },
  {
    path: '/route-one',
    name: 'DataCharts',
    component: DataCharts
  },
  {
    path: '/route-two',
    name: 'DataQuery',
    component: DataQuery,
    meta:{
        keepAlive:true
    }
  },
  {
    path: '/route-three',
    name: 'GroupSearch',
    component: GroupSearch
  },
  {
    path: '/route-four',
    name: 'ApiManage',
    component: ApiManage
  },
  {
    path: '/route-five',
    component: SqlManage,
    children: [
      {
        path: '',
        name: 'SqlMainpage',
        component: SqlMainpage,
        meta:{
          keepAlive:false
      }
      },
      {
        path: 'create-table',
        name: 'CreateTable',
        component: CreateTable
      },
      {
        path: 'table-detail/:tableName',
        name: 'DataTable',
        component: DataTable,
        props: true,
        meta:{
          keepAlive:false
      }
      },
      {
        path: 'table-edit/:tableName',
        name: 'EditTable',
        component: EditTable,
        props: true
      }
    ]
  },
  {
    path: '/route-six',
    component: AuthorManage,
    children: [
      {
        path: '',
        name: 'CheckAuthor',
        component: CheckAuthor,
        meta:{
          keepAlive:false
      }
      },
      {
        path: 'edit-author',
        name: 'EditAuthor',
        component: EditAuthor,
      },
      {
        path: 'delete-author/:username',
        name: 'DeleteAuthor',
        component: DeleteAuthor,
        props: true,
        meta:{
          keepAlive:false
      }
      },
      {
        path: 'add-author',
        name: 'AddAuthor',
        component: AddAuthor,
      }
    ]
  },
  // 其他路由配置...
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});
router.beforeEach((to, from, next) => {
    // 假设路由元信息包含了应该激活的菜单项索引
    if (to.meta.activeIndex) {
      store.dispatch('updateActiveIndex', to.meta.activeIndex);
    }
    next();
  });
  
export default router;
