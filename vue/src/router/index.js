import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}//解决导航栏重复点击报错

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home', //重定向到主页
    children: [
      { path: 'home', name: 'Home', meta: { name: '首页' }, component: () => import('../views/manager/Home') },
      { path: 'productDetail', name: 'ProductDetail', meta: { name: '商品详情' }, component: () => import('../views/manager/ProductDetail') },
      { path: 'digitaDevice', name: 'DigitaDevice', meta: { name: '数码' }, component: () => import('../views/manager/DigitaDevice') },
      { path: 'user', name: 'User', meta: { name: '用户管理' }, component: () => import('../views/manager/User') },
      { path: 'product', name: 'Product', meta: { name: '商品管理' }, component: () => import('../views/manager/Product') },
      { path: '403', name: 'Auth', meta: { name: '无权限' }, component: () => import('../views/manager/Auth') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/manager/Person') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'MyProducts', name: 'myProducts', meta: { name: '我发布的' }, component: () => import('../views/manager/MyProducts') },
      { path: 'test', name: 'test', meta: { name: '测试' }, component: () => import('../views/manager/test') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: '404', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // to是到达路由信息，from是来源的路由信息，next是帮助我们跳转路由的函数 
  let adminPaths = ['/user', '/product']
  let user = JSON.parse(localStorage.getItem('SecondHand-User') || '{}')
  if (user.role !== '管理员' && adminPaths.includes(to.path)) {
    //如果当前登录的用户不是管理员， 然后当前的到达路径是管理员才能权限访问的路径，这时就让用户去到一个没有权限的页面，不访问实际的页面
    next('/403')
  } else {
    next()
  }
})

export default router