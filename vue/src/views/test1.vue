<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
      <Aside />
      <el-container>
        <!--        头部区域-->
        <el-header>
          <div style="color: rgb(144,147,153);">欢迎你，{{ user.username }}</div>
          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
          </div>
        </el-header>
        <!--        主体区域-->
        <el-main>
         <div style="display: flex">
           <el-card style="width: 50%;margin-right: 10px">
             <div slot="header" class="clearfix">
               <span>test</span>
             </div>
             <div>
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
  components:{
    Aside,
  }
}
</script>
