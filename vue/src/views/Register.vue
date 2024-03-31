<template>
  <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #67C23A;">
    <!--align-items: center; justify-content: center;垂直水平居中-->

    <!-- 把这个flex平均拆成两部分 -->
    <div
      style="display: flex; background-color: white; width: 500px; height: 750px; border-radius: 5px; overflow: hidden;">

      <!-- <div style="flex: 1">
        <img src="@/assets/register.png" alt="" style="width: 100%;"> 
      </div> -->

      <div style="flex: 1; display: flex; align-items: center; justify-content: center;">
        <el-form :model="user" style="width: 80%;" :rules="rules" ref="registerRef">

          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px;">欢迎注册培正校园二手网</div>

          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" size="medium" v-model="user.username" placeholder="用户名"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password v-model="user.password"
              placeholder="密码"></el-input>
          </el-form-item>

          <el-form-item prop="confirmPassword">
            <el-input prefix-icon="el-icon-circle-check" size="medium" show-password style="flex: 1;"
              v-model="user.confirmPassword" placeholder="确认密码"></el-input>
          </el-form-item>

          <el-form-item prop="nickname">
            <el-input prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;" v-model="user.nickname"
              placeholder="昵称"></el-input>
          </el-form-item>

          <el-form-item prop="role">
            <el-select v-model="user.role" placeholder="身份">
              <el-option label="学生" value="学生"></el-option>
              <el-option label="老师" value="老师"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item prop="wechat">
            <el-input prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;" v-model="user.wechat"
              placeholder="微信"></el-input>
          </el-form-item>

          <el-form :model="user" :rules="rules" ref="mailRef">
            <el-form-item prop="email">
              <el-input prefix-icon="el-icon-circle-check" size="medium" style="flex: 1;" v-model="user.email"
                placeholder="邮箱"></el-input>
            </el-form-item>
          </el-form>

          <el-form-item prop="code" style="display: flex;">
            <el-input prefix-icon="el-icon-circle-check" size="medium" v-model="user.code" placeholder="邮箱验证码"
              style="width: 200px;"></el-input>
            <el-button style="margin-left: 10px;" @click="getCode" :disabled="getCodeButtonIsDisabled">获取验证码</el-button>
          </el-form-item>

          <el-form-item>
            <el-button type="success" style="width: 100%;" @click="register">注 册</el-button>
          </el-form-item>
          <div style="display: flex;">
            <div style="flex: 1;">已经有账号？去 <span style="color:#67C23A; cursor: pointer;"
                @click="$router.push('/login')">登录</span></div>
          </div>
        </el-form>
      </div>

    </div>

  </div>
</template>

<script>

export default {
  name: "Register",

  data() {
    //确认密码校验
    const validatePassword = (rule, confirmPassword, callback) => {
      if (confirmPassword === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPassword !== this.user.password) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }//这一串直接从官网拿的


    return {
      getCodeButtonIsDisabled: false,//绑定获取验证码按钮
      user: {
        username: '',
        password: '',
        confirmPassword: '',//二次确认密码
        nickname: '',
        role: '',
        wechat: '',
        email: '',
        code: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 1, max: 30, message: '微信号长度在20个字符以内', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 1, max: 30, message: '长度在30个字符以内', trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择身份', trigger: 'blur' },
        ],
        wechat: [
          { required: true, message: '请输入微信，买家或卖家将通过微信联系你', trigger: 'blur' },
          { min: 1, max: 30, message: '长度在30个字符以内', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱，邮箱是找回密码的重要凭据', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] },
          { min: 1, max: 30, message: '长度在30个字符以内', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入获取的邮箱验证码', trigger: 'blur' },
        ],
      },

    }
  },
  created() {

  },
  methods: {
    register() {
      this.$refs['registerRef'].validate((valid) => {
        if (valid) {
          //验证通过
          this.$request.post('/register', this.user).then(res => {
            if (res.code == 200) {
              this.$router.push('/login')//登录成功后跳转到登录界面
              this.$message.success('注册成功')//用消息组件弹出登录成功
            } else {
              this.$message.error(res.msg)//用消息组件将错误信息弹出
            }
          })
        }
      })
    },
    getCode() {
      this.$refs['mailRef'].validate((valid) => {

        if (valid) {
          this.getCodeButtonIsDisabled = true
          this.$request.get('/getCodeForReg/' + this.user.email).then(res => {
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

        } else {
          this.$message.error('请填写邮箱后获取验证码')
        }
      })
    }

  },

}
</script>

<style scoped></style>