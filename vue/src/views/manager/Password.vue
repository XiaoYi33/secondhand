<template>
    <div>
        <el-card style="width: 35%;">
            <el-form ref="formRef" :rules="rules" :model="user" label-width="80px" style="padding-right: 20px;" >
                <el-form-item label="原始密码" prop="password">
                    <el-input v-model="user.password" placeholder="原始密码" show-password> </el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPassword">
                    <el-input v-model="user.newPassword" placeholder="新密码" show-password></el-input>
                </el-form-item><el-form-item label="确认密码" prop="confirmPassword" show-password>
                    <el-input v-model="user.confirmPassword" placeholder="确认密码"></el-input>
                </el-form-item>
                <div style="text-align: center;">
                    <el-button type="primary" @click="update">确认修改</el-button>
                </div>
            </el-form>
        </el-card>

    </div>
</template>

<script>
export default {
    name: 'Password',
    data() {
        //验证码校验
        const validatePassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请确认密码'))
            } else if (value!==this.user.newPassword) {
                callback(new Error('确认密码错误'))
            } else {
                callback()
            }
        }
        return {
            user: JSON.parse(localStorage.getItem('SecondHand-User' || '{}')),
            rules: {
                password: [
                    { required: true, message: '请输入当前密码', trigger: 'blur' },
                ],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                ],
                confirmPassword: [
                    { validator: validatePassword, required: true,requtrigger: 'blur' },
                ],

            }
        }
    },
    methods: {
        update() {
            //更新数据库当前用户信息
            this.$refs.formRef.validate((valid) => {
                //valid为true时触发更新
                if (valid) {
                    this.user.password=this.user.newPassword
                    this.$request.put('/user/update', this.user).then(res => {
                        if (res.code === '200') {
                            this.$message.success('保存成功')
                            
                            this.$router.push('/login')

                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            })


        }
    }
}
</script>

<style scoped>
/deep/.el-form-item__label {
    font-weight: bold;
}
</style>