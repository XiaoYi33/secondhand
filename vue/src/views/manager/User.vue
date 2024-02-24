<template>
    <div>
        <!-- 搜索框和按钮 -->
        <div style="display: flex; margin: 10px 0;">
            <div style="flex:1">
                <el-input style="width: 200px;" placeholder="输入要查询的用户名" v-model="username"></el-input>
                <el-input style="width: 200px; margin: 0 5px;" placeholder="输入要查询用户的昵称" v-model="nickname"></el-input>
                <el-button type="success" @click="reacher">查询</el-button>
                <el-button type="info" @click="reset">重置</el-button>
            </div>
            <div style="flex:1 right: inherit;">
                <el-button type="primary" @click="handleAdd">新增</el-button>
                <el-button type="danger" @click="deleteBatch">批量删除</el-button>
            </div>
        </div>

        <!-- 表格 -->
        <el-table :data="tableData" stripe @selection-change="handleSelectionChange" >
            <el-table-column type="selection" width="55" align="center" :selectable="isRowSelectable">
            </el-table-column>
            <el-table-column prop="id" label="ID" align="center" width="55"></el-table-column>
            <el-table-column prop="username" label="用户名" align="center"></el-table-column>
            <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>
            <el-table-column prop="wechat" label="微信" align="center"></el-table-column>
            <el-table-column prop="phone" label="电话" align="center"></el-table-column>
            <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
            <el-table-column label="头像">
                <template v-slot="scope">
                    <el-avatar v-if="scope.row.avatar" :src="scope.row.avatar"></el-avatar>
                </template>
            </el-table-column>
            <el-table-column prop="role" label="身份"></el-table-column>
            <el-table-column label="操作" align="center" width="180px">
                <template v-slot="scope">
                    <el-button type="primary" plain @click="handleEdit(scope.row)" v-if="scope.row.role !== '管理员'">
                        编辑</el-button>
                    <el-button type="danger" plain @click="handleDelete(scope.row.id)"
                        v-if="scope.row.role !== '管理员'">删除</el-button>
                </template>

            </el-table-column>
        </el-table>
        <!-- 分页按钮 -->
        <div style="margin: 10px 0;">
            <span class="demonstration"></span>
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNum" :page-sizes="[100, 200, 300, 400]"
                :page-size="pageSize" layout="total, prev, pager, next" :total="total">
            </el-pagination>
        </div>

        <!-- 新增和编辑用户弹出对话框 -->
        <el-dialog title="新增编辑用户" :visible.sync="formVisible" width="25%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px;" :rules="rules" ref="formRef">
                <div style="margin: 15px; text-align: center;">
                    <el-upload class="avatar-uploader" action="http://localhost:9090/file/upload"
                        :headers="{ token: user.token }" :show-file-list="false" :on-success="handleAvatarSuccess">
                        <img v-if="form.avatar" :src="form.avatar" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </div>
                <el-form-item label="用户名" prop="username" v-if="!form.id">
                    <el-input v-model="form.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="username" v-if="form.id">
                    <el-input v-model="form.username" placeholder="用户名" disabled></el-input>
                </el-form-item>
                <el-form-item label="昵称" prop="nickname">
                    <el-input v-model="form.nickname" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item label="角色" prop="role">
                    <el-select v-model="form.role" placeholder="请选择身份">
                        <el-option label="学生" value="学生"></el-option>
                        <el-option label="老师" value="老师"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="微信" prop="wechat">
                    <el-input v-model="form.wechat" placeholder="微信"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                    <el-input v-model="form.phone" placeholder="电话"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="formVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>


    </div>
</template>

<script>
export default {
    name: 'User',
    data() {
        return {
            tableData: [],
            pageNum: 1, //当前页码
            pageSize: 10, //每页显示的数据个数
            username: '',//绑定查询输入框
            nickname: '',//绑定查询输入框
            total: 0, //分页总数，动态获取
            formVisible: false,
            form: {},
            user: JSON.parse(localStorage.getItem('SecondHand-User')),
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                ],
                role: [
                    { required: true, message: '请输入选择身份', trigger: 'blur' },
                ],
                email: [
                    { required: true, message: '请输入邮箱', trigger: 'blur' },
                ],
            },
            ids: [],//用于批量删除，id数组
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.$request.get('/admin/user/selectByPage', {
                params: {
                    pageNumber: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    nickname: this.nickname
                }
            }).then(res => {
                this.tableData = res.data.records.map(item => {//在后端数据原有的基础上多加disabled属性，用来判断多选框是否可以被选中
                    return {
                        ...item,
                        disabled: item.role !== '管理员'
                    }
                })
                this.total = res.data.total
            });
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum
            this.load()
        },
        reset() {//绑定重置按钮
            this.username = '',
                this.nickname = '',
                this.load()
        },
        reacher() {//绑定查询按钮
            this.pageNum = 1,
            this.pageSize = 10,
            this.load()
        },
        handleAvatarSuccess(response, file, fileList) {//绑定新增用户弹窗里的上传头像按钮
            this.form.avatar = response.data
        },
        save() {//绑定新增/编辑用户信息的保存按钮
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: this.form.id ? '/admin/user/update' : '/admin/user/add',
                        method: this.form.id ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {//表示成功保存
                            this.$message.success('保存成功')
                            this.load()//刷新页面
                            this.formVisible = false
                        } else {
                            this.$message.error(res.msg)//弹出错误信息
                        }
                    })
                }
            })
        },
        handleAdd() {//绑定新增按钮
            this.form = {}//新增数据时清空数据
            this.formVisible = true//打开新增用户弹窗
        },
        handleEdit(row) {//绑定编辑按钮
            this.form = JSON.parse(JSON.stringify(row)) //给form对象赋值，深拷贝数据
            this.formVisible = true //打开弹窗
        },
        handleDelete(id) {
            this.$confirm('是否确认删除用户', '确认删除', { type: "warning" }).then(res => {
                this.$request.delete('/admin/user/delete/' + id).then(res => {
                    if (res.code === '200') {
                        this.$message.success('删除成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })

            }).catch(() => { })
        },
        handleSelectionChange(rows) {//绑定el-table标签，用于批量删除,rows为当前选中所有行的数据
            this.ids = rows.map(v => v.id) //将rows数组中的id转成数字数组

        },
        deleteBatch(ids) {//绑定批量删除按钮
            if (!this.ids.length) {//当id数组为空时提示
                this.$message.warning("请选择数据")
                return
            }
            this.$confirm('是否确认批量删除用户', '确认删除', { type: "warning" }).then(res => {
                this.$request.delete('/admin/user/delete/batch', { data: this.ids }).then(res => {
                    if (res.code === '200') {
                        this.$message.success('删除成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })

            }).catch(() => { })
        },
        isRowSelectable(row, index) {
            return row.disabled; // 只有当行的 disabled 属性为 true 时才可选，不是管理员的row都是true
        },
    },
}
</script>

<style scoped>
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