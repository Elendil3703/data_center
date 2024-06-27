import Vue from 'vue';
import VueRouter from 'vue-router';
import DataQuery from '@/Page/DataQuery.vue'; // 确保路径正确
import GroupSearch from '@/Page/GroupSearch.vue';
import DataCharts from '@/Page/DataCharts.vue';
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
import MyTables from '@/Page/MyTables.vue';
Vue.use(VueRouter);

const routes = [
  {
    path: '/home',
    component: DataQuery,
    meta: { requiresAuth: true }
  },
  {
    path: '/',
    name:'Login',
    component:LoginRoot,
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
    path: '/route-five',
    component: SqlManage,
    //meta: { requiresAuth: true },
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
    //meta: { requiresAuth: true },
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
  {
    path:'/route-seven',
    name:'MyTables',
    component:MyTables,
    meta:{
      keepAlive:true
    }
  }
  // 其他路由配置...
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('jwt');
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isAuthenticated) {
      if (to.name !== 'Login') {
        next({ name: 'Login' });
      } else {
        next();
      }
    } else {
      next();
    }
  } else {
    next();
  }
});
export default router;
