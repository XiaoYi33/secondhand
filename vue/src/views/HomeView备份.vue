<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
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

      <el-container>
        <!--        头部区域-->
        <el-header>

          <!-- <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>  todo:收缩按钮，估计用不上-->
          <!-- <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/user' }">用户管理</el-breadcrumb-item>
          </el-breadcrumb> todo:顶部导航栏，估计用不上-->
          <div style="color: rgb(144,147,153);">欢迎你，{{ user.username }}</div>

          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <!-- <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i> -->
            <!-- <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px; margin: 0 5px">
                <span>管理员</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown> -->
            <!-- el组件屏蔽了@click -->
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
import request from "@/utils/request"
import Aside from "@/views/Aside.vue"

export default {
  name: 'HomeView',
  data() {
    return {
      avatar:'@/assets/logo1.png',//测试用头像
      users:[],
      user:{}
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

<style>
.el-menu--inline .el-menu-item {
  /* background-color: #000c17 !important; */
  padding-left: 49px !important;
  font-weight: normal;
  
}
/* .el-menu-item:hover, .el-submenu__title:hover {
  color: #fff !important;
}
.el-submenu__title:hover i {
  color: #fff !important;
  
}
.el-menu-item:hover i {
  color: #fff !important;
}  */
.el-submenu .el-menu-item:hover{
  color: #fff !important;
}
.el-menu-item.is-active {
  border-radius: 5px !important;
  width: calc(100% - 15px);
  /* margin-left: 4px; */
}
/* .el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip{
  margin-left: -4px;
} */
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
/* .el-menu--inline .el-menu-item.is-active {
  padding-left: 45px !important;
} */

.el-aside {
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
}
.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;   /* 0.3s */
}
.el-header {
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  display: flex;
  align-items: center;
}

</style>