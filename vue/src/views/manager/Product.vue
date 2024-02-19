<template>
    <div>
        <!-- 搜索框和按钮 -->
        <div style="display: flex; margin: 10px 0;">
            <div style="flex:1">
                <el-input style="width: 200px;" placeholder="输入要查询的商品编号" v-model="productId"></el-input>
                <el-input style="width: 200px; margin: 0 0 0 5px;" placeholder="输入要查询的商品名称"
                    v-model="productName"></el-input>
                <el-input style="width: 200px; margin: 0 0 0 5px;" placeholder="输入要查询的用户名" v-model="username"></el-input>
                <el-input style="width: 200px; margin: 0 5px;" placeholder="输入要查询的用户昵称" v-model="nickname"></el-input>
                <el-button type="success" @click="reacher">查询</el-button>
                <el-button type="info" @click="reset">重置</el-button>
            </div>
            <div style="flex:1 right: inherit;">
                <el-button type="danger" @click="deleteBatch">批量删除</el-button>
            </div>
        </div>

         <!-- 表格 -->
         <el-table :data="tableData" stripe @selection-change="handleSelectionChange" >
            <el-table-column type="selection" width="55" align="center" :selectable="isRowSelectable">
            </el-table-column>
            <el-table-column prop="id" label="ID" align="center" width="55"></el-table-column>
            <el-table-column prop="name" label="商品名称" align="center"></el-table-column>
            <el-table-column prop="image" label="图片" align="center"></el-table-column>
            <el-table-column prop="categoryName" label="类别" align="center"></el-table-column>
            <el-table-column prop="price" label="价格" align="center"></el-table-column>
            <el-table-column prop="username" label="发布人" align="center"></el-table-column>
            <el-table-column prop="state" label="状态" align="center"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" align="center"></el-table-column>
            <el-table-column label="操作" align="center" width="180px">
                <template v-slot="scope">
                    <el-button type="primary" plain @click="handleEdit(scope.row)" >编辑</el-button>
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
        <!-- 表格 -->
        
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

        }
    },
    created() {
        this.load()//页面创建时调用Load方法
    },
    methods: {
        //方法
        load() {
            // this.$request.get('/product/selectByPage', {
            //     params: {
            //         pageNumber: this.pageNum,
            //         pageSize: this.pageSize,
            //         username: this.username,
            //         nickname: this.nickname,
            //         productId: this.productId,
            //         productName: this.productName,
            //     }
            // }).then(res => {
            //     this.tableData = res.data.records
            //     this.total = res.data.total
            // });

            this.$request.get('/product/selectAll').then(res=>{
                this.tableData=res.data
            })
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
                this.nickname = '',
                this.load()
        }

    }
}
</script>

<style scoped></style>