<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
      <el-aside width="200px" style="min-height: 100vh; background-color: #ffffff">
        <div style="height: 180px; color: white; display: flex; align-items:end; justify-content: center">

          <img v-if="user.avatar" :src="user.avatar" class="avatar"
            style="display: block; width: 150px; height: 150px; border-radius: 50%; border: 10px;">

        </div>
        <div style="display: flex; font-size: smaller; justify-content: center;align-items:center;height: 28px;">
          {{ user.nickname }}
        </div>
        <div style="display: flex; font-size: smaller; justify-content: center;align-items:start;height: 28px;">
          <div
            style="border-radius:5px; border: 1px solid rgb(25,156,96); width: 50px; justify-content: center;text-align: center; color: rgb(25,156,96); height: 20px; line-height: 20px;">
            {{ user.role }}</div>
        </div>


        <el-menu router background-color="#ffffff" text-color="rgb(144,147,153)" active-text-color="rgb(25,156,96)"
          style="border: none;" :default-active="$route.path">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-submenu index="type">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>分类</span>
            </template>
            <el-menu-item index="/digitaDevice">数码</el-menu-item>
            <el-menu-item index="/Appliance">电器</el-menu-item>
            <el-menu-item index="/Furniture">家具</el-menu-item>
            <el-menu-item index="/Books">书籍</el-menu-item>
            <el-menu-item index="/Snacks">零食</el-menu-item>
          </el-submenu>
          <el-submenu index="adminTool" v-if="user.role === '管理员' || user.role === '站长'">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span>后台管理</span>
            </template>
            <el-menu-item index="/user">用户管理</el-menu-item>
            <el-menu-item index="/product">商品管理</el-menu-item>
            <el-menu-item index="/transaction">订单管理</el-menu-item>
            <el-menu-item>数据统计</el-menu-item>
          </el-submenu>

          <el-submenu index="center">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <span>个人中心</span>
            </template>
            <el-menu-item index="/person">我的信息</el-menu-item>
            <el-menu-item index="/myProducts">我发布的</el-menu-item>
            <el-menu-item index="/purchasedProducts">我买的</el-menu-item>
            <el-menu-item index="/soldProducts">我卖的</el-menu-item>
            <!-- <el-menu-item index="">我的评价</el-menu-item> -->
            <el-menu-item index="/password">修改密码</el-menu-item>
            <el-menu-item @click.native="logout">退出登录</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <!--        头部区域-->
        <el-header>

          <!-- 导航栏 -->
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px;width: 300px;" >
            <el-breadcrumb-item :to="{ path: '/home' }">校园二手交易平台</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
          </el-breadcrumb>

          <!-- 搜索框 -->
          <el-input placeholder="请输入要搜索的商品" v-model="productName" style="width: 400px;margin-left: 100px;">
            <el-select v-model="productCategory" slot="prepend" placeholder="全部" style="width: 80px;">
              <el-option label="全部" value="null"></el-option>
              <el-option label="数码" value="数码"></el-option>
              <el-option label="电器" value="电器"></el-option>
              <el-option label="家具" value="家具"></el-option>
              <el-option label="书籍" value="书籍"></el-option>
              <el-option label="零食" value="零食"></el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>

        </el-header>

        <!--        主体区域-->
        <el-main>
          <router-view @update:user="updateUser"></router-view>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>

export default {
  name: 'HomeView',
  data() {
    return {
      //todo 侧边栏收缩的话这里要加变量
      user: JSON.parse(localStorage.getItem('SecondHand-User') || '{}'),
      productName:null,
      productCategory:null,
    }
  },
  methods: {
    updateUser(user) {//获取子组件传过来的数据，更新当前页面的数据
      this.user = JSON.parse(JSON.stringify(user))//让父级的对象跟子级对象毫无关联
    },

    //todo 侧边栏收缩的话这里要加方法
    logout() {
      this.$router.push('/login')
      localStorage.removeItem('SecondHand-User')//退出登录时清除token
    },
    async search(){
      if(this.productCategory==='null')this.productCategory=null
     await this.$router.push({
                name: 'SearchResult',
                query: {
                    productName:this.productName,
                    productCategory:this.productCategory
                }
            })
      location.reload();
    }
    
  }
}
</script>

<style>
.el-menu--inline .el-menu-item {
  padding-left: 49px !important;
  font-weight: normal;
}

.el-submenu .el-menu-item:hover {
  color: #fff !important;
  background-color: rgb(25, 156, 96) !important;
}

.el-menu-item:hover {
  color: #fff !important;
  background-color: rgb(25, 156, 96) !important;
}

.el-menu-item.is-active {
  /* border-radius: 5px !important; */
  background-color: rgb(242, 241, 246);
  /* width: calc(100% - 15px); */
}

.el-menu-item {
  height: 40px !important;
  line-height: 40px !important;
  font-size: 18px;
  font-weight: bold;
  margin: 5px 5px 5px 10px;
  border-radius: 5px !important;
}

.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
  font-weight: bold;
  font-size: 18px;
  margin: 5px 5px 5px 10px;
  border-radius: 5px !important;
}

.el-submenu .el-menu-item {
  min-width: 0 !important;
  height: 40px !important;
  font-size: 17px !important;
}

.el-aside {
  transition: width .3s;
  /* box-shadow: 2px 0 6px rgba(0, 21, 41, .35); */
  box-shadow: 6px 0 6px rgb(242, 241, 246);
}

.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;
  /* 0.3s */
}

.el-main {
  background-color: rgb(242, 241, 246);
}

.el-header {
  /* box-shadow: 2px 0 6px rgba(0, 21, 41, .35); */
  display: flex;
  align-items: center;
}

</style>