import Vue from 'vue'
import App from './App.vue'
import router from './router'
import '@/assets/css/global.css'

Vue.config.productionTip = false

//引入vue和element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import request from "@/utils/request"
Vue.use(ElementUI);

Vue.prototype.$request=request //定义全局变量request
Vue.prototype.$baseUrl=process.env.VUE_APP_BASEURL
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')



