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
      { path: 'searchResult', name: 'SearchResult', meta: { name: '搜索结果' }, component: () => import('../views/manager/SearchResult') },
      { path: 'productDetail', name: 'ProductDetail', meta: { name: '商品详情' }, component: () => import('../views/manager/ProductDetail') },
      { path: '403', name: 'Auth', meta: { name: '无权限' }, component: () => import('../views/manager/Auth') },

      { path: 'digitaDevice', name: 'DigitaDevice', meta: { name: '数码' }, component: () => import('../views/manager/category/DigitaDevice') },
      { path: 'appliance', name: 'Appliance', meta: { name: '电器' }, component: () => import('../views/manager/category/Appliance') },
      { path: 'furniture', name: 'Furniture', meta: { name: '家具' }, component: () => import('../views/manager/category/Furniture') },
      { path: 'books', name: 'Books', meta: { name: '书籍' }, component: () => import('../views/manager/category/Books') },
      { path: 'snacks', name: 'Snacks', meta: { name: '零食' }, component: () => import('../views/manager/category/Snacks') },

      { path: 'user', name: 'User', meta: { name: '用户管理' }, component: () => import('../views/manager/admintools/User') },
      { path: 'product', name: 'Product', meta: { name: '商品管理' }, component: () => import('../views/manager/admintools/Product') },
      { path: 'transaction', name: 'Transaction', meta: { name: '订单管理' }, component: () => import('../views/manager/admintools/Transaction') },

      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/manager/personal center/Person') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/personal center/Password') },
      { path: 'MyProducts', name: 'myProducts', meta: { name: '我发布的' }, component: () => import('../views/manager/personal center/MyProducts') },
      { path: 'PurchasedProducts', name: 'purchasedProducts', meta: { name: '我买的' }, component: () => import('../views/manager/personal center/PurchasedProducts') },
      { path: 'buyerTransactionDetail', name: 'BuyerTransactionDetail', meta: { name: '订单详情' }, component: () => import('../views/manager/personal center/BuyerTransactionDetail') },
      { path: 'soldProducts', name: 'SoldProducts', meta: { name: '我卖的' }, component: () => import('../views/manager/personal center/SoldProducts') },
      { path: 'sellerTransactionDetail', name: 'SellerTransactionDetail', meta: { name: '订单详情' }, component: () => import('../views/manager/personal center/SellerTransactionDetail') },
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
  let adminPaths = ['/user', '/product','/order']
  let user = JSON.parse(localStorage.getItem('SecondHand-User') || '{}')
  if (user.role !== '管理员' && adminPaths.includes(to.path)) {
    //如果当前登录的用户不是管理员， 然后当前的到达路径是管理员才能权限访问的路径，这时就让用户去到一个没有权限的页面，不访问实际的页面
    next('/403')
  } else {
    next()
  }
})

export default router