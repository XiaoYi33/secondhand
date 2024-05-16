<template>
  <div style="
       height: 100vh;
       display: flex;
       align-items: center;
       justify-content: center;
       background-image: url(https://img0.baidu.com/it/u=3996078719,2754082441&fm=253&fmt=auto&app=138&f=PNG?w=667&h=500);
       background-size: cover;
       background-position: center;
       background-repeat: no-repeat;
  ">
    <!--align-items: center; justify-content: center;垂直水平居中-->

    <!-- 把这个flex平均拆成两部分 -->
    <div style="width: 30%; border-radius: 5px;background-color: rgba(255, 255, 255, 0.4); overflow: hidden;">
      <div style="flex: 1; display: flex; align-items: center; justify-content: center;">
        <el-form :model="user" style="width: 80%;" :rules="rulesForLogin" ref="loginRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px;">欢迎登录广应科二手易物平台</div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" size="medium" v-model="user.username" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password v-model="user.password"
              placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex;">
              <el-input prefix-icon="el-icon-circle-check" size="medium"  style="flex: 1;" v-model="user.code"
                placeholder="请输入验证码"></el-input>
              <div style="flex: 0.5%; height: 36px;width: 5px;">
                <valid-code @update:value="getCode" ></valid-code>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%; background-color: cornflowerblue" @click="login">登 录</el-button>
          </el-form-item>
          <div style="display: flex;">
            <div style="flex: 1;">还没有账号？请 <span style="color:#409EFF; cursor: pointer;"
                @click="$router.push('/register')">注册</span></div>
            <div style="flex: 1; text-align: right;"><span style="color:#409EFF; cursor: pointer;"
                @click="forgetPasswordButton">忘记密码</span></div>
          </div>
        </el-form>
      </div>

    </div>

    <!-- 忘记密码对话框 -->
    <el-dialog title="忘记密码" :visible.sync="forgetPasswordDialogVisible" width="500px">
      <el-form :model="forgetUser" label-width="100px" style="padding-right: 20px" :rules="rulesForForget"
        ref="forgetRef">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="forgetUser.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="passowrd">
          <el-input v-model="forgetUser.password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="forgetUser.confirmPassword" placeholder="请确认密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱验证码" prop="code">
          <el-input v-model="forgetUser.code" placeholder="请输入验证码" style="width: 200px;"></el-input>
          <el-button style="margin-left: 10px;" @click="getEmailCode"
            :disabled="getCodeButtonIsDisabled">获取验证码</el-button>
        </el-form-item>
        <div style="text-align: center;">
          <el-button type="success" @click="forgetPassword">确认</el-button>
          <el-button type="danger" @click="cancelForgetPassword">取消</el-button>
        </div>
      </el-form>
    </el-dialog>

  </div>



</template>

<script>
import ValidCode from "@/components/ValidCode";

export default {
  name: "Login",
  components: {
    ValidCode
  },

  data() {
    //确认密码校验
    const validatePassword = (rule, confirmPassword, callback) => {
      if (confirmPassword === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPassword !== this.forgetUser.password) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }

    //验证码校验
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else if (value.toLowerCase() !== this.code) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    }//这一串直接从官网拿的

    return {
      forgetPasswordDialogVisible: false,//绑定忘记密码对话框
      getCodeButtonIsDisabled: false,//绑定获取验证码按钮
      code: '',//验证码组件传递过来的Code
      user: {
        username: '',
        password: '',
        code: '',//登录验证码
      },
      forgetUser: {
        username: '',
        password: '',
        confirmPassword: '',
        code: '',//邮箱验证码
      },
      rulesForLogin: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ],


      },
      rulesForForget: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入获取的邮箱验证码', trigger: 'blur' },
        ],
      }
    }
  },
  created() {

  },
  methods: {
    getCode(code) {
      console.log("生成的验证码:" + code)
      this.code = code.toLowerCase()
      console.log("小写转化后的验证码：" + this.code)
    },
    login() {
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          //验证通过
          this.$request.post('/login', this.user).then(res => {
            if (res.code == 200) {
              this.$router.push('/')//登录成功后跳转到主页
              this.$message.success('登录成功')//用消息组件弹出登录成功
              localStorage.setItem("SecondHand-User", JSON.stringify(res.data))//存储用户数据
            } else {
              this.$message.error(res.msg)//用消息组件将错误信息弹出
            }
          })
        }
      })
    },
    forgetPasswordButton() {
      this.forgetPasswordDialogVisible = true
    },
    getEmailCode() {
      this.getCodeButtonIsDisabled = true
      this.$request.get('/getCode/' + this.forgetUser.username).then(res => {
        if (res.code == 200) {
          this.$message.success('获取验证码成功，请到邮箱查看')
          setTimeout(() => {
            this.getCodeButtonIsDisabled = false;
          }, 30000);
        } else {
          this.getCodeButtonIsDisabled = false;
          this.$message.error(res.msg)
        }
      })

    },
    forgetPassword() {
      this.$refs['forgetRef'].validate((valid) => {
        if (valid) {
          this.$request.put('/resetPassword',this.forgetUser).then(res => {
            if (res.code == 200) {
              this.forgetPasswordDialogVisible = false
              this.forgetUser = {},
              this.$message.success('找回密码成功')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    cancelForgetPassword() {
      this.forgetUser = {},
      this.forgetPasswordDialogVisible = false
    }

  },

}
</script>

<style scoped></style>