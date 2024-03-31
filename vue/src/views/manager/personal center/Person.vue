<template>
    <div style="display: flex; ">
        <el-card style="width: 35%; ">
            <el-form :model="user" label-width="80px" style="padding-right: 20px;" :rules="rules" ref="personRef">
                <div style="margin: 15px; text-align: center;">
                    <el-upload class="avatar-uploader" action="http://localhost:9090/file/upload"
                        :headers="{ token: user.token }" :show-file-list="false" :before-upload="beforeAvatarUpload"
                        :on-success="handleAvatarSuccess">
                        <img v-if="user.avatar" :src="user.avatar" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </div>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="user.username" placeholder="用户名" disabled></el-input>
                </el-form-item>
                <el-form-item label="昵称" prop="nickname">
                    <el-input v-model="user.nickname" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="角色" prop="role" v-if="user.role !== '管理员' && user.role !== '站长'">
                    <el-select v-model="user.role" placeholder="请选择身份">
                        <el-option label="学生" value="学生"></el-option>
                        <el-option label="老师" value="老师"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="user.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="微信" prop="wechat">
                    <el-input v-model="user.wechat" placeholder="微信"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" prop="createTime">
                    <el-input v-model="user.createTime" placeholder="创建时间" disabled></el-input>
                </el-form-item>
                <div style="text-align: center; margin-bottom: 20px;">
                    <el-button type="success" @click="update">保存</el-button>
                </div>
            </el-form>
        </el-card>
    </div>
</template>

<script>
export default {
    name: 'Person',
    data() {
        return {
            user: JSON.parse(localStorage.getItem('SecondHand-User') || '{}'),

            rules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                    { min: 1, max: 30, message: '微信号长度在20个字符以内', trigger: 'blur' }
                ],
                nickname: [
                    { required: true, message: '请输入昵称', trigger: 'blur' },
                    { min: 1, max: 30, message: '长度在30个字符以内', trigger: 'blur' }
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
            }
        }
    },
    methods: {
        handleAvatarSuccess(response, file, fileList) {
            this.user.avatar = response.data
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
            const isLt5M = file.size / 1024 / 1024 < 5;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
            }
            if (!isLt5M) {
                this.$message.error('上传头像图片大小不能超过 5MB!');
            }
            return isJPG && isLt5M;
        },
        update() {
            this.$refs['personRef'].validate((valid) => {
                if (valid) {
                    //更新数据库当前用户信息
                    this.$request.put('/user/update', this.user).then(res => {
                        if (res.code === '200') {
                            this.$message.success('保存成功')
                            //数据库更新成功后，再更新浏览器本地用户信息
                            localStorage.setItem('SecondHand-User', JSON.stringify(this.user))

                            //触发Manager.vue数据更新
                            this.$emit('update:user', this.user)

                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }else{this.$message.error('请检查信息是否填写完整')}
            })

        }
    }
}
</script>

<style scoped>
/deep/.el-form-item__label {
    font-weight: bold;
}

/deep/.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 50%;
    cursor: pointer;
    position: relative;
    overflow: hidden;

}

/deep/.el-upload {
    border-radius: 50%;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
    border-radius: 50%;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
    border-radius: 50%;
}

.el-button--success {
    background-color: rgb(25, 156, 96);
    border-color: rgb(25, 156, 96);
}

.el-button--success:hover {
    background-color: rgb(31, 173, 109);
    border-color: rgb(31, 173, 109);
}
</style>