<template>
  <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #409EFF;">
    <!--align-items: center; justify-content: center;垂直水平居中-->

    <!-- 把这个flex平均拆成两部分 -->
    <div style="display: flex; background-color: white; width: 50%; border-radius: 5px; overflow: hidden;">

      <div style="flex: 1">
        <!-- 图片一定要设置宽 -->
        <img src="@/assets/login.png" alt="" style="width: 100%;"> 
      </div>

      <div style="flex: 1; display: flex; align-items: center; justify-content: center;">
        <el-form :model="user" style="width: 80%;" :rules="rules" ref="loginRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px;">欢迎登录培正校园二手网</div>
          <el-form-item  prop="username" >
            <el-input prefix-icon="el-icon-user" size="medium" v-model="user.username" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item  prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password v-model="user.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item  prop="code">
            <div style="display: flex;">
              <el-input prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;" v-model="user.code" placeholder="请输入验证码"></el-input>
              <div style="flex: 1; height: 36px;" >
                <valid-code @update:value="getCode"></valid-code>
              </div>
            </div>
          </el-form-item>
          <el-form-item >
            <el-button type="primary" style="width: 100%;" @click="login">登 录</el-button>
          </el-form-item>
          <div style="display: flex;">
            <div style="flex: 1;">还没有账号？请 <span style="color:#409EFF; cursor: pointer;" @click="$router.push('/register')">注册</span></div>
            <div style="flex: 1; text-align: right;"><span style="color:#409EFF; cursor: pointer;">忘记密码</span></div>
          </div>
        </el-form>
      </div>

    </div>

  </div>
</template>

<script>
import ValidCode from "@/components/ValidCode";

export default {
  name: "Login",
  components:{
    ValidCode
  },

  data(){
    //验证码校验
    const validateCode = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入验证码'))
        } else if(value.toLowerCase() !== this.code){
          callback(new Error('验证码错误'))
        }else{
          callback()
        }
    }//这一串直接从官网拿的

    return{
      code:'',//验证码组件传递过来的Code
      user:{
        username:'',
        password:'',
        code:'',//用户输入的Code
      },
      rules:{
        username:[
          {required: true, message:'请输入账号', trigger:'blur'},
        ],
        password:[
          {required: true, message:'请输入密码', trigger:'blur'},
        ],
        code:[
          {validator: validateCode,trigger:'blur'}
        ],
        
      }
    }
  },
  created(){

  },
  methods: {
    getCode(code){
      console.log("生成的验证码:"+code)
      this.code=code.toLowerCase()
      console.log("小写转化后的验证码："+this.code)
    },
    login(){
      this.$refs['loginRef'].validate((valid)=>{
        if(valid){
          //验证通过
          this.$request.post('/login',this.user).then(res=>{
            if(res.code==200){
              this.$router.push('/')//登录成功后跳转到主页
              this.$message.success('登录成功')//用消息组件弹出登录成功
              localStorage.setItem("honey-user",JSON.stringify(res.data))//存储用户数据
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