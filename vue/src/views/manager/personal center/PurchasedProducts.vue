<template>
    <el-container>
        <el-main>
            <!-- 状态按钮 -->
            <div style="display: flex; margin-bottom: 30px; margin-left: 30px;">
                <el-button v-if="this.transactionState === '待交易'" class="button" round
                    style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                    @click="changeState('待交易')">待交易({{ this.total }})</el-button>
                <el-button v-if="this.transactionState === '已完成'" class="button" round
                    style="border:0px;  background-color:rgb(242, 241, 246) ;" @click="changeState('已完成')">已完成</el-button>
                <el-button v-if="this.transactionState === '待交易'" class="button" round
                    style="border:0px;  background-color:rgb(242, 241, 246) ;" @click="changeState('待交易')">待交易</el-button>
                <el-button v-if="this.transactionState === '已完成'" class="button" round
                    style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                    @click="changeState('已完成')">已完成({{ this.total }})</el-button>
            </div>

            <el-card :body-style="{ padding: '0px' }">

            </el-card>


        </el-main>

    </el-container>
</template>

<script>
export default {
    name:'PurchasedProducts',
    data() {
        return {
           pageNumber:1,
           pageSize:20,
           total:0,
           user: JSON.parse(localStorage.getItem('SecondHand-User')),//获取当前登录用户
           transactionState:'待交易'
        }
    },
    created() {
        this.load()
    },
    methods: {
        load(){
            this.$request.get('/transaction/selectAllInfoByPage',{
                params:{
                    pageSize:this.pageSize,
                    pageNumber:this.pageNumber,
                    userId:this.user.id,
                    transactioState:this.transactionState
                }

            }).then(res=>{

            })
        },
        changeState(state){
            this.transactionState=state
        }
    }
}
</script>

<style scoped>
.button:hover {
    background-color: #fff !important;
    color: #606266 !important;
}

.button:active {
    background-color: #fff !important;
    color: #606266 !important;
}
</style>