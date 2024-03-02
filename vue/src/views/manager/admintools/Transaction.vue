<template>
    <div>
        <!-- 搜索框和按钮 -->
        <div style="display: flex; margin: 10px 0;">
            <div style="flex:1">
                <el-input style="width: 200px; margin: 0 5px;" placeholder="输入要查询的订单编号" v-model="transactionId"></el-input>
                <el-button type="success" @click="reacher">查询</el-button>
                <el-button type="info" @click="reset">重置</el-button>
            </div>
            <div style="flex:1 right: inherit;">
                <el-button type="danger" @click="deleteBatch">批量删除</el-button>
            </div>
        </div>

        <el-table :data="tableData" stripe @selection-change="handleSelectionChange" v-if="tableData">
            <el-table-column type="selection" width="55" align="center" >
            </el-table-column>
            <el-table-column prop="id" label="订单编号" align="center"></el-table-column>
            <el-table-column prop="buyer_username" label="买家用户名" align="center"></el-table-column>
            <el-table-column prop="product_id" label="商品编号" align="center"></el-table-column>
            <el-table-column prop="seller_username" label="卖家用户名" align="center"></el-table-column>
            <el-table-column prop="create_time" label="创建时间" align="center"></el-table-column>
            <el-table-column prop="state" label="状态" align="center"></el-table-column>
            <el-table-column label="操作" align="center" width="100px">
                <template v-slot="scope">
                    <el-button type="danger" plain @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页按钮 -->
        <div style="margin: 10px 0;">
            <span class="demonstration"></span>
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNumber" :page-sizes="[100, 200, 300, 400]"
                :page-size="pageSize" layout="total, prev, pager, next" :total="total">
            </el-pagination>
        </div>




    </div>
</template>

<script>
export default {
    name: 'Transaction',
    data() {
        return {
            tableData: [],
            transactionId: null,//绑定查询输入框
            pageNumber: 1, //当前页码
            pageSize: 10, //每页显示的数据个数
            total: 0, //分页总数，动态获取
            ids: [],//用于批量删除，id数组
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.$request.get('/admin/transaction/selectPageById',{
                params:{
                    pageNumber:this.pageNumber,
                    pageSize: this.pageSize,
                    id:this.transactionId
                }
            }).then(res=>{
                this.tableData=res.data.records
                this.total=res.data.total
            })
        },
        reacher() {//绑定查询按钮
            this.load()
        },
        reset() {//绑定重置按钮
            this.transactionId=null
            this.load()
        },
        deleteBatch() {//绑定批量删除按钮
            if (!this.ids.length) {//当id数组为空时提示
                this.$message.warning("请选择数据")
                return
            }
            this.$confirm('是否确认批量删除订单', '确认删除', { type: "warning" }).then(res => {
                this.$request.delete('/admin/transaction/delete/batch', { data: this.ids }).then(res => {
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
        handleDelete(id) {
            this.$confirm('是否确认删除订单', '确认删除', { type: "warning" }).then(res => {
                this.$request.delete('/admin/transaction/delete/' + id).then(res => {
                    if (res.code === '200') {
                        this.$message.success('删除成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })

            }).catch(() => { })
        },
        handleCurrentChange(pageNumber) {
            this.pageNumber = pageNumber
            this.load()
        },
    }
}
</script>

<style scoped></style>