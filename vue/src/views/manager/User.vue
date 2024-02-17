<template>
    <div>
        <div style="display: flex; margin: 10px 0;">
            <div style="flex:1">
                <el-input style="width: 200px;" placeholder="输入要查询的用户名" v-model="username"></el-input>
                <el-input style="width: 200px; margin: 0 5px;" placeholder="输入要查询的昵称" v-model="name"></el-input>
                <el-button type="success" @click="reacher">查询</el-button>
                <el-button type="info" @click="reset">重置</el-button>
            </div>
            <div style="flex:1 right: inherit;">
                <el-button type="primary">新增</el-button>
                <el-button type="danger">批量删除</el-button>
            </div>
        </div>

        <el-table :data="tableData" stripe>
            <el-table-column type="selection" width="55" align="center">
            </el-table-column>
            <el-table-column prop="id" label="ID" align="center" width="55"></el-table-column>
            <el-table-column prop="username" label="用户名" align="center"></el-table-column>
            <el-table-column prop="name" label="昵称" align="center"></el-table-column>
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
                    <el-button type="primary" plain>编辑</el-button>
                    <el-button type="danger" plain>删除</el-button>
                </template>

            </el-table-column>
        </el-table>
        <div style="margin: 10px 0;">
            <span class="demonstration"></span>
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNum" :page-sizes="[100, 200, 300, 400]"
                :page-size="pageSize" layout="total, prev, pager, next" :total="total">

            </el-pagination>
        </div>
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
            name: '',//绑定查询输入框
            total: 0 //分页总数，动态获取
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.$request.get('/user/selectByPage', {
                params: {
                    pageNumber: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    name: this.name
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum
            this.load()
        },
        reset() {
            this.username = '',
                this.name = '',
                this.load()
        },
        reacher() {
            this.pageNum = 1,
                this.pageSize = 10,
                this.load()
        }
    },
}
</script>

<style scoped></style>