<template>
  <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #67C23A;">
    <!--align-items: center; justify-content: center;垂直水平居中-->

    <!-- 把这个flex平均拆成两部分 -->
    <div style="display: flex; background-color: white; width: 50%; border-radius: 5px; overflow: hidden;">

      <div style="flex: 1">
        <!-- 图片一定要设置宽 -->
        <img src="@/assets/register.png" alt="" style="width: 100%;"> 
      </div>

      <div style="flex: 1; display: flex; align-items: center; justify-content: center;">
        <el-form :model="user" style="width: 80%;" :rules="rules" ref="registerRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px;">欢迎注册培正校园二手网</div>
          <el-form-item  prop="username" >
            <el-input prefix-icon="el-icon-user" size="medium" v-model="user.username" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item  prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password v-model="user.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item  prop="confirmPassword">
            <el-input prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;" v-model="user.confirmPassword" placeholder="请确认密码"></el-input>
          </el-form-item>
          <el-form-item >
            <el-button type="success" style="width: 100%;" @click="register">注 册</el-button>
          </el-form-item>
          <div style="display: flex;">
            <div style="flex: 1;">已经有账号？去 <span style="color:#67C23A; cursor: pointer;" @click="$router.push('/login')">登录</span></div>
          </div>
        </el-form>
      </div>

    </div>

  </div>
</template>

<script>

export default {
  name: "Register",

  data(){
    //确认密码校验
    const validatePassword = (rule, confirmPassword, callback) => {
        if (confirmPassword === '') {
          callback(new Error('请确认密码'))
        } else if(confirmPassword !== this.user.password){
          callback(new Error('两次输入密码不一致'))
        }else{
          callback()
        }
    }//这一串直接从官网拿的


    return{
      user:{
        username:'',
        password:'',
        confirmPassword:'',//二次确认密码
      },
      rules:{
        username:[
          {required: true, message:'请输入账号', trigger:'blur'},
        ],
        password:[
          {required: true, message:'请输入密码', trigger:'blur'},
        ],
        confirmPassword:[
        {validator: validatePassword,trigger:'blur'}
        ],
        
      }
    }
  },
  created(){

  },
  methods: {
    register(){
      this.$refs['registerRef'].validate((valid)=>{
        if(valid){
          //验证通过
          this.$request.post('/register',this.user).then(res=>{
            if(res.code==200){
              this.$router.push('/login')//登录成功后跳转到登录界面
              this.$message.success('注册成功')//用消息组件弹出登录成功
            }else{
              this.$message.error(res.msg)//用消息组件将错误信息弹出
            }
          })
        }
      })
    },
     
  },

}
</script>

<style scoped>

</style>