<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
      <el-aside width="200px" style="min-height: 100vh; background-color: #ffffff">
        <!-- <div style="display: flex;color: rgb(144,147,153); justify-content: center; align-items:center; text-align: center; height: 40px;">欢迎登录培正校园二手网</div> -->
        <div style="height: 180px; color: white; display: flex; align-items:end; justify-content: center">
          
          <img v-if="user.avatar" :src="user.avatar" class="avatar" style="display: block; width: 150px; height: 150px; border-radius: 50%;">

        </div>
        <div style="display: flex; font-size: smaller; justify-content: center;align-items:center;height: 28px;">
          {{ user.name }}
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
            <el-menu-item index="/test1">数码</el-menu-item>
            <el-menu-item index="/test2">电器</el-menu-item>
            <el-menu-item index="/Furniture">家具</el-menu-item>
            <el-menu-item index="/Books">书籍</el-menu-item>
            <el-menu-item index="">零食</el-menu-item>
          </el-submenu>
          <el-submenu index="adminTool" v-if="user.role==='管理员'">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span>后台管理</span>
            </template>
            <el-menu-item index="/user">用户管理</el-menu-item>
            <el-menu-item index="/product">商品管理</el-menu-item>
            <el-menu-item index="">订单管理</el-menu-item>
          </el-submenu>
          
          <el-submenu index="rootTool" v-if="user.role==='站长'">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span>后台管理</span>
            </template>
            <el-menu-item index="/user">用户管理</el-menu-item>
            <el-menu-item index="/product">商品管理</el-menu-item>
            <el-menu-item index="/order">订单管理</el-menu-item>
            <el-menu-item index="">数据统计</el-menu-item>
          </el-submenu>
          <el-submenu index="center">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <span>个人中心</span>
            </template>
            <el-menu-item>我的信息</el-menu-item>
            <el-menu-item>我发布的</el-menu-item>
            <el-menu-item index="">我买的</el-menu-item>
            <el-menu-item index="">我卖的</el-menu-item>
            <el-menu-item index="">我的评价</el-menu-item>
            <el-menu-item index="">修改密码</el-menu-item>
            <el-menu-item @click.native="logout">退出登录</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <!--        头部区域-->
        <el-header>
          <div style="color: rgb(144,147,153);">欢迎登录培正校园二手网</div>
          <!-- <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
          </div> -->
        </el-header>

        <!--        主体区域-->
        <el-main>
          <router-view></router-view>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request"

export default {
  name: 'HomeView',
  data() {
    return {
      users: [],
      user: JSON.parse(localStorage.getItem('SecondHand-User') || '{}')
    }
  },
  mounted() {
    request.get('/user/selectAll').then(res => {
      this.users = res.data
    })


  },
  methods: {
    logout() {
      this.$router.push('/login')
      localStorage.removeItem('SecondHand-User')//退出登录时清除token
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
  background-color: rgb(25,156,96) !important; 
}
.el-menu-item:hover{
  color: #fff !important;
  background-color: rgb(25,156,96) !important; 
}

.el-menu-item.is-active {
  border-radius: 5px !important;
  width: calc(100% - 15px);
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
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
}

.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;
  /* 0.3s */
}

.el-header {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
  display: flex;
  align-items: center;
}</style>