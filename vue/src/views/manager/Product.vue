<template>
    <div>
        <!-- 搜索框和按钮 -->
        <div style="display: flex; margin: 10px 0;">
            <div style="flex:1">
                <el-input style="width: 200px;" placeholder="输入要查询的商品编号" v-model="productId"></el-input>
                <el-input style="width: 200px; margin: 0 0 0 5px;" placeholder="输入要查询的商品名称"
                    v-model="productName"></el-input>
                <el-input style="width: 200px; margin: 0 5px;" placeholder="输入要查询的用户名" v-model="username"></el-input>
                <el-button type="success" @click="reacher">查询</el-button>
                <el-button type="info" @click="reset">重置</el-button>
            </div>
            <div style="flex:1 right: inherit;">
                <el-button type="danger" @click="deleteBatch">批量删除</el-button>
            </div>
        </div>

        <!-- 表格 -->
        <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center">
            </el-table-column>
            <el-table-column prop="id" label="ID" align="center" width="55"></el-table-column>
            <el-table-column prop="name" label="商品名称" align="center"></el-table-column>
            <el-table-column label="图片" align="center">
                <template v-slot="scope">
                    <el-image style="width: 70px; height: 70px;" v-if="scope.row.image" :src="scope.row.image" :preview-src-list="[scope.row.image]"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="category.name" label="类别" align="center"></el-table-column>
            <el-table-column prop="price" label="价格" align="center"></el-table-column>
            <el-table-column prop="user.username" label="用户名" align="center"></el-table-column>
            <el-table-column prop="state" label="状态" align="center"></el-table-column>
            <el-table-column label="操作" align="center" width="180px">
                <template v-slot="scope">
                    <el-button type="success" plain @click="handleCheck(scope.row)">查看</el-button>
                    <el-button type="danger" plain @click="handleDelete(scope.row.id)">删除</el-button>
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
        <!-- 查看商品弹出对话框 -->
        <el-dialog title="查看商品" :visible.sync="formVisible" width="70%">
            <div style="display: flex; align-items: center; justify-content: center; ">
                <div style="text-align: center; width: 60%; height: 100%;">
                    <el-image style="height: 100%; width: 100%;" :src="form.image" fit="cover"
                        :preview-src-list="[form.image]"></el-image>
                </div>
                <div style="width: 40%; padding-left: 5%;">
                    <el-form :model="form">
                        <el-form-item label="名称：">{{ form.name }}</el-form-item>
                        <el-form-item label="状态：">{{ form.state }}</el-form-item>
                        <el-form-item label="分类：">{{ form.category.name }}</el-form-item>
                        <el-form-item label="描述：">{{ form.description }}</el-form-item>
                        <el-form-item label="价格：">{{ form.price }}</el-form-item>
                        <el-form-item label="用户名：">{{ form.user.username }}</el-form-item>
                        <el-form-item v-if="form.reason" label="下架原因：">{{ form.reason }}</el-form-item>
                        <el-form-item label="创建时间：">{{ form.createTime }}</el-form-item>
                        <el-form-item label="更新时间：">{{ form.updateTime }}</el-form-item>
                    </el-form>
                    <el-button v-if="form.state !== '下架'" type="warning" style="width: 80%;"
                        @click="takeDownDialogVisible = true">下架</el-button>
                </div>
            </div>
        </el-dialog>

        <!-- 下架商品弹出对话框 -->
        <el-dialog title="确认下架" :visible.sync="takeDownDialogVisible" width="30%">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <el-input v-model="form.reason" placeholder="请输入下架原因" style="margin-right: 5px;"  maxlength="40" show-word-limit></el-input>
                <el-button type="warning" @click="takeDown" style="margin-left: auto;">提交</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
export default {
    name: 'Product',
    data() {
        //数据
        return {
            productId: '',//商品Id，绑定查询框
            productName: '',//商品名，绑定查询框
            username: '',//用户名，绑定查询框
            nickname: '',//用户昵称，绑定查询框
            tableData: [],//表单数据，接收后端查询返回的数据
            pageNum: 1, //当前页码
            pageSize: 10, //每页显示的数据个数
            total: 0, //分页总数，动态获取
            form: {
                user: {
                    username: ''
                },
                category: {
                    name: ''
                }
            },//绑定编辑商品对话框，
            formVisible: false,//绑定编辑商品对话框,true则弹出对话框
            takeDownDialogVisible: false,//确认下架对话框


        }
    },
    created() {
        this.load()//页面创建时调用Load方法
    },
    methods: {
        //方法
        load() {
            this.$request.get('/admin/product/selectByParams', {
                params: {
                    pageNumber: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    productId: this.productId,
                    productName: this.productName,
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            });
            

        },
        reacher() {//绑定查询按钮
            this.pageNum = 1,
                this.pageSize = 10,
                this.load()
        },
        reset() {//绑定重置按钮
            this.productId = '',
                this.productName = '',
                this.username = '',
                this.load()
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum
            this.load()
        },
        handleDelete(id) {//绑定删除按钮
            this.$confirm('是否确认删除商品', '确认删除', { type: "warning" }).then(res => {
                this.$request.delete('/admin/product/delete/' + id).then(res => {
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
        handleCheck(row) {//绑定查看按钮
            this.form = JSON.parse(JSON.stringify(row)) //给form对象赋值，深拷贝数据
            this.formVisible = true //打开弹窗
        },

        handleAvatarSuccess(response, file, fileList) {//绑定编辑商品弹窗里的上传图片按钮
            this.form.avatar = response.data
        },
        takeDown() {//绑定查看商品对话框的下架按钮
            if(!this.form.reason){
                this.$message.error('原因不能为空')
                return 
            }
            //更新数据库当前商品信息
            this.form.state = "下架"
            
            this.$request.put('/admin/product/update', this.form).then(res => {
                if (res.code === '200') {
                    this.$message.success('下架成功')
                    this.formVisible = false
                    this.takeDownDialogVisible = false
                    this.load()
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        deleteBatch(ids) {//绑定批量删除按钮
            if (!this.ids.length) {//当id数组为空时提示
                this.$message.warning("请选择数据")
                return
            }
            this.$confirm('是否确认批量删除商品', '确认删除', { type: "warning" }).then(res => {
                this.$request.delete('/admin/product/delete/batch', { data: this.ids }).then(res => {
                    if (res.code === '200') {
                        this.$message.success('删除成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })

            }).catch(() => { })
        },

    }
}
</script>
 
<style scoped></style>