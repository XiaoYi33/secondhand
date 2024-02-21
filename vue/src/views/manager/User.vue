<template>
    <div>
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="ID" align="center"></el-table-column>
            <el-table-column prop="username" label="用户名" align="center"></el-table-column>
            <el-table-column prop="name" label="昵称" align="center"></el-table-column>
            <el-table-column prop="phone" label="电话" align="center"></el-table-column>
            <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
            <el-table-column prop="address" label="地址" align="center"></el-table-column>
            <el-table-column label="头像">
                <template v-slot="scope">
                    <el-avatar v-if="scope.row.avatar" :src="scope.row.avatar" ></el-avatar>
                </template>
            </el-table-column>
            <el-table-column prop="role" label="身份"></el-table-column>
            <el-table-column label="操作">
                <template v-slot="scope">
                    <el-button type="primary" plain>编辑</el-button>
                    <el-button type="danger" plain>删除</el-button>
                </template>

            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: 'User',
    data() {
        return {
            tableData: [],
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.$request.get('/user/selectAll').then(res => {
                this.tableData = res.data
            })
        }
    },
}
</script>

<style></style>