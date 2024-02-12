<template>
    <div style="display: flex; ">
        <el-card style="width: 35%; ">
            <div style="margin: 15px; text-align: center;">
                <el-upload class="avatar-uploader" action="http://localhost:9090/file/upload"
                    :headers="{ token: user.token }" :show-file-list="false" :before-upload="beforeAvatarUpload"
                    :on-success="handleAvatarSuccess">

                    <img v-if="user.avatar" :src="user.avatar" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </div>
            <el-form :model="user" label-width="80px" style="padding-right: 20px;" :rules="rules">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="user.username" placeholder="用户名" disabled></el-input>
                </el-form-item>
                <el-form-item label="昵称" prop="name">
                    <el-input v-model="user.name" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="角色" prop="role" v-if="user.role!=='管理员'&&user.role!=='站长'">
                    <el-select v-model="user.role" placeholder="请选择身份">
                        <el-option label="学生" value="学生"></el-option>
                        <el-option label="老师" value="老师"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="user.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="QQ" prop="qq">
                    <el-input v-model="user.qq" placeholder="QQ"></el-input>
                </el-form-item>
                <el-form-item label="微信" prop="wechat">
                    <el-input v-model="user.wechat" placeholder="微信"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                    <el-input v-model="user.phone" placeholder="电话"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" prop="create_time">{{ user.create_time }}
                </el-form-item>
                <div style="text-align: center; margin-bottom: 20px;">
                    <el-button type="primary" @click="update">保存</el-button>
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
            user: JSON.parse(localStorage.getItem('SecondHand-User')),

            rules: {
                name: [
                    { required: true, message: '请输入昵称', trigger: 'blur' },
                ],
                email: [
                    { required: true, message: '请输入邮箱', trigger: 'blur' },
                ],

            }
        }
    },
    methods: {
        handleAvatarSuccess(response, file, fileList) {
            this.user.avatar = response.data
        },
        beforeAvatarUpload() {

        },
        update() {
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
</style>