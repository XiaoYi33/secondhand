import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home', //重定向到主页
    children: [
      { path: 'home', name: 'Home', component: () => import('../views/manager/Home') },
      { path: 'user', name: 'User', component: () => import('../views/manager/User') },
      { path: 'product', name: 'Product', component: () => import('../views/manager/Product') },
      { path: '403', name: 'Auth', component: () => import('../views/manager/Auth') },
    ]
  },
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('../views/Register.vue') },
  { path: '*', name: '404', component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // to是到达路由信息，from是来源的路由信息，next是帮助我们跳转路由的函数 
  let adminPaths = ['/user','/product']
  let user = JSON.parse(localStorage.getItem('SecondHand-User') || '{}')
  if (user.role !== '管理员' && user.role !== '站长' && adminPaths.includes(to.path)) {
    //如果当前登录的用户不是管理员， 然后当前的到达路径是管理员才能权限访问的路径，这时就让用户去到一个没有权限的页面，不访问实际的页面
    next('/403')
  } else {
    next()
  }
})

export default router