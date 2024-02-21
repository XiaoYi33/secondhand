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
            <!-- <div style="flex:1 right: inherit;">
                <el-button type="danger" @click="deleteBatch">批量删除</el-button>
            </div> -->
        </div>

         <!-- 表格 -->
         <el-table :data="tableData" stripe @selection-change="handleSelectionChange" >
            <el-table-column type="selection" width="55" align="center" >
            </el-table-column>
            <el-table-column prop="id" label="ID" align="center" width="55"></el-table-column>
            <el-table-column prop="name" label="商品名称" align="center"></el-table-column>
            <el-table-column label="图片" align="center">
                <template v-slot="scope">
                    <el-image style="width: 70px; height: 70px;" v-if="scope.row.image" :src="scope.row.image" :preview-src-list="[scope.row.image]"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="category.name" label="类别" align="center" ></el-table-column>
            <el-table-column prop="price" label="价格" align="center"></el-table-column>
            <el-table-column prop="user.username" label="用户名" align="center"></el-table-column>
            <el-table-column prop="state" label="状态" align="center" ></el-table-column>
            <el-table-column label="操作" align="center" width="180px">
                <template v-slot="scope">
                    <el-button type="success" plain @click="handleCheck(scope.row)" >查看</el-button>
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
        <el-dialog title="" :visible.sync="formVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px;" >
              <el-form-item label="商品名称" prop="name">{{ form.name }}</el-form-item>
              <el-form-item label="状态" prop="state">{{ form.state }}</el-form-item>
              <el-form-item label="用户名" prop="user">{{ form.user.username }}</el-form-item>
              <el-form-item label="商品描述" prop="description">{{ form.description }}</el-form-item>
              <el-form-item label="价格" prop="price">{{ form.price }}</el-form-item>
              <el-form-item label="创建时间" prop="createTime">{{ form.createTime }}</el-form-item>
              <el-form-item label="更新时间" prop="updateTime">{{ form.updateTime }}</el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="text-align:center">
                <el-button type="warning" @click="takeDown()">下架</el-button>
                <el-button @click="formVisible = false">取 消</el-button>
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
            form:{
                user:{
                    username:''
                }
            },//绑定编辑商品对话框，
            formVisible:false,//绑定编辑商品对话框,true则弹出对话框
            

        }
    },
    created() {
        this.load()//页面创建时调用Load方法
    },
    methods: {
        //方法
        load() {
            this.$request.get('/product/selectByParams', {
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
                this.$request.delete('/product/delete/' + id).then(res => {
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
            console.log(this.form.user.username)
            this.formVisible = true //打开弹窗
        },
        
        handleAvatarSuccess(response, file, fileList) {//绑定编辑商品弹窗里的上传图片按钮
            this.form.avatar = response.data
        },
        takeDown(){

        },

    }
}
</script>
 
<style scoped>
</style>