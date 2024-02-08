<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
      <el-aside width="200px" style="min-height: 100vh; background-color: #ffffff">
        <div style="height: 200px; color: white; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/logo1.png" alt="" style="width: 150px; height: 150px"><!--todo:这里是头像-->
        </div>
        <div>

        </div>

        <el-menu  router background-color="#ffffff" text-color="rgb(144,147,153)" active-text-color="rgb(25,156,96)" style="border: none;" :default-active="$route.path">
          <el-menu-item index="/">
            <i class="el-icon-menu"></i>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>分类</span>
            </template>
            <el-menu-item index="/test1">数码</el-menu-item>
            <el-menu-item index="/test2">电器</el-menu-item>
            <el-menu-item index="/Furniture">家具</el-menu-item>
            <el-menu-item index="/Books">书籍</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
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

          <!-- <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>  todo:收缩按钮，估计用不上-->
          <!-- <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/user' }">用户管理</el-breadcrumb-item>
          </el-breadcrumb> todo:顶部导航栏，估计用不上-->

          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px; margin: 0 5px">
                <span>管理员</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                <!-- el组件屏蔽了@click -->
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-header>

        <!--        主体区域-->
        <el-main>
          <!-- <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
            早安，骚年，祝你开心每一天！
          </div> --><!--todo:用不上，可以参考样式-->
         <div style="display: flex">
           <el-card style="width: 50%;margin-right: 10px">
             <div slot="header" class="clearfix">
               <span>青哥哥带你做毕设2024</span>
             </div>
             <div>
               2024毕设正式开始了！青哥哥带你手把手敲出来！
               <div style="margin-top: 20px">
                 <div style="margin: 10px 0"><strong>主题色</strong></div>
                 <el-button type="primary">按钮</el-button>
                 <el-button type="success">按钮</el-button>
                 <el-button type="warning">按钮</el-button>
                 <el-button type="danger">按钮</el-button>
                 <el-button type="info">按钮</el-button>
               </div>
             </div>
           </el-card>

           <el-card style="width: 50%; margin-right: 10px">
             <div slot="header" class="clearfix">
               <span>渲染用户的数据</span>
             </div>
             <div>
               <el-table :data="users">
                  <el-table-column label="ID" prop="id"></el-table-column>
                  <el-table-column label="用户名" prop="username"></el-table-column>
                  <el-table-column label="姓名" prop="name"></el-table-column>
                  <el-table-column label="地址" prop="address"></el-table-column>
               </el-table>
             </div>
           </el-card>
         </div>
        </el-main>

      </el-container>


    </el-container>
  </div>
</template>

<script>
import axios from 'axios'
import request from "@/utils/request"

export default {
  name: 'HomeView',
  data() {
    return {
      users:[]
    }
  },
  
  mounted(){
    // axios.get('http://localhost:9090/user/selectAll').then(res=>{
    //   console.log(res.data)//后台返回的数据
    //   this.users=res.data.data //后台返回的数据给users数组
    // })
    
    // axios.get('http://localhost:9090/user/selectById/17').then(res=>{
    //   console.log("selectById查询的数据"+res.data)//后台返回的数据
    //   this.users=res.data //后台返回的数据给users数组
    // })

      request.get('/user/selectAll').then(res=>{
        this.users=res.data
      })

  },

  methods: {
    handleFull() {
      document.documentElement.requestFullscreen()
    },
    logout(){
      this.$router.push('/login')
      localStorage.removeItem('honey-user')//退出登录时清除token
    }
  }
}
</script>
