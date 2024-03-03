<template>
    <el-container>
        <el-main>
            <!-- 状态按钮 -->
            <div style="display: flex; margin-bottom: 30px; margin-left: 30px;">
                <el-button v-if="this.transactionState === '待交易'" class="button" round
                    style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                    @click="changeState('待交易')">待交易({{ this.total }})</el-button>

                <el-button v-if="this.transactionState === '待交易'" class="button" round
                    style="border:0px;  background-color:rgb(242, 241, 246) ;" @click="changeState('完成')">已完成</el-button>

                <el-button v-if="this.transactionState === '完成'" class="button" round
                    style="border:0px;  background-color:rgb(242, 241, 246) ;" @click="changeState('待交易')">待交易</el-button>

                <el-button v-if="this.transactionState === '完成'" class="button" round
                    style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                    @click="changeState('完成')">已完成({{ this.total }})</el-button>

            </div>

            <el-card :body-style="{ padding: '0px' }"  v-for="transaction in transactions" :key="transaction.id"
                style="float: left; margin-left: 30px ; margin-bottom: 30px; width: 500px;" v-if="transaction">
                
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
           pageSize:24,
           total:0,
           user: JSON.parse(localStorage.getItem('SecondHand-User')),//获取当前登录用户
           transactionState:'待交易',
           transactions:[],
        }
    },
    created() {
        this.load()
    },
    methods: {
        load(){
            this.total=0
            this.transactions=[]
            this.$request.get('/transaction/selectAllInfoByBuyerId',{
                params:{
                    pageSize:this.pageSize,
                    pageNumber:this.pageNumber,
                    userId:this.user.id,
                    transactionState:this.transactionState
                }

            }).then(res=>{
                this.total=res.data.total
                this.transactions=res.data.records

            })
        },
        changeState(state){
            this.transactionState=state
            this.load()
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