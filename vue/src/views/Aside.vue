<template>
    <el-aside width="200px" style="min-height: 100vh; background-color: #ffffff">
        <div style="height: 180px; color: white; display: flex; align-items:end; justify-content: center">
          <!-- <img src="@/assets/logo1.png" alt="" style="width: 150px; height: 150px">todo:这里是头像 -->
          <el-avatar :size="150" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
          
        </div>
        <div style="display: flex; font-size: smaller; justify-content: center;align-items:end;height: 30px;">
          <div style="border-radius:5px; border: 1px solid rgb(25,156,96); width: 60px; justify-content: center;text-align: center; color: rgb(25,156,96); height: 20px; line-height: 20px;">管理员</div>
          
        </div>
        

        <el-menu  router background-color="#ffffff" text-color="rgb(144,147,153)" active-text-color="rgb(25,156,96)" style="border: none;" :default-active="$route.path">
          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
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
  </template>
  
<script>
import request from "@/utils/request"

export default {
  data() {
    return {
      user:{}
    }
  },
  mounted(){
    request.get('/user/selectById/1').then(res=>{
      this.user=res.data
    })
  },
  methods: {
    logout(){
      this.$router.push('/login')
      localStorage.removeItem('honey-user')//退出登录时清除token
    }
  }
}
</script>