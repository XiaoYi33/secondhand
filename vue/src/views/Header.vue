<template>
    <el-header>
        <div style="color: rgb(144,147,153);">欢迎你，{{ user.username }}</div>
        <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
        </div>
    </el-header>
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
    }
  },
}
</script>

<style>
.el-header {
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  display: flex;
  align-items: center;
}
</style>