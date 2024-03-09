<template>
    <el-container>
        <el-main>

            <!-- 状态按钮 -->
            <div style="display: flex; margin-bottom: 30px; margin-left: 30px;">
                <div>
                    <el-button class="stateButton" v-if="this.transactionState === null"  round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState(null)">全部({{ total }})</el-button>
                    <el-button v-if="this.transactionState !== null" class="stateButton" round
                        style="border:0px;  background-color:rgb(242, 241, 246) ;" @click="changeState(null)">全部</el-button>
                </div>

                <div>
                    <el-button v-if="this.transactionState === '待交易'" class="stateButton" round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState('待交易')">待交易({{ total }})</el-button>
                    <el-button v-if="this.transactionState !== '待交易'" class="stateButton" round
                        style="border:0px;  background-color:rgb(242, 241, 246) ;"
                        @click="changeState('待交易')">待交易</el-button>
                </div>

                <div>
                    <el-button v-if="this.transactionState === '待确认'" class="stateButton" round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState('待确认')">待确认({{ total }})</el-button>
                    <el-button v-if="this.transactionState !== '待确认'" class="stateButton" round
                        style="border:0px;  background-color:rgb(242, 241, 246) ;"
                        @click="changeState('待确认')">待确认</el-button>
                </div>

                <div>
                    <el-button v-if="this.transactionState === '已完成'" class="stateButton" round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState('已完成')">已完成({{ total }})</el-button>
                    <el-button v-if="this.transactionState !== '已完成'" class="stateButton" round
                        style="border:0px;  background-color:rgb(242, 241, 246) ;"
                        @click="changeState('已完成')">已完成</el-button>
                </div>

                <div>
                    <el-button v-if="this.transactionState !== '已取消'" class="stateButton" round
                        style="border:0px;  background-color:rgb(242, 241, 246) ;"
                        @click="changeState('已取消')">已取消</el-button>
                    <el-button v-if="this.transactionState === '已取消'" class="stateButton" round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState('已取消')">已取消({{ total }})</el-button>
                </div>
                <div style="display: flex; margin-left: 50px;">
                    <el-input v-model="productName" placeholder="请输入要查找的商品名"></el-input>
                    <el-button type="success" icon="el-icon-search" style="margin-left: 5px;" @click="search"></el-button>
                    <el-button type="info" icon="el-icon-refresh-left" style="margin-left: 5px;" @click="reset"></el-button>
                </div>



            </div>

            <el-card :body-style="{ padding: '10px' }" v-for="transaction in transactions" :key="transaction.id"
                style="float: left; margin-left: 30px ; margin-bottom: 30px; width: 500px;" v-if="transaction">

                <!-- 买家头像和昵称和订单状态-->
                <div style="display: flex; margin-bottom: 5px;">
                    <div style="flex:0;margin-right: 5px;">
                        <el-image :src="transaction.buyer_avatar" lazy
                            style="width: 30px; height: 30px; border-radius: 50%; border: 10px;"></el-image>
                    </div>
                    <div style="height: 30px; width:250px;display: flex; align-items: center;font-size: 14px;">{{
                        transaction.buyer_nickname }}</div>
                    <div v-if="transaction.comment === ''"
                        style="height: 30px; width:200px; align-items: center;text-align: right; font-size: 15px;color: rgb(25,156,96);">
                        {{ transaction.state }}</div>
                    <div v-if="transaction.comment !== ''"
                        style="height: 30px; width:200px;align-items: center;text-align: right; font-size: 15px;color: rgb(25,156,96);">
                        {{ transaction.comment }}</div>
                </div>

                <!-- 商品图片+信息 -->
                <div style="display: flex;" @click="showDetail(transaction.id)">
                    <div style="flex:0">
                        <el-image :src="transaction.product_image" lazy
                            style="width: 80px; height: 80px; border-radius: 4%;"></el-image>
                    </div>
                    <div style="flex:content; margin-left: 5px;">
                        <span
                            style="display: flex; height: 80px; align-items: center;font-weight: bold; font-size: 16px;">{{
                                transaction.product_name }}</span>
                    </div>
                    <div style="flex:0;">
                        <div style="display: flex; height: 80px; align-items: center;">
                            <span style="font-size: 15px; color: rgb(244, 73, 61); ">¥</span>
                            <span style="font-size: 20px; color: rgb(244, 73, 61); font-weight: bold;">{{
                                transaction.price }}</span>
                        </div>
                    </div>
                </div>

                <el-divider></el-divider>
                <!-- 操作按钮 -->
                <div style="width: 480px; display: flex;">
                    <div style="width: 80px;  ">
                        <el-button type="text" style="color:rgb(122,122,122); font-size: 13px; height: 30px;"
                            v-if="transaction.state === '待交易'" @click="cancelTransaction(transaction)">取消订单</el-button>
                    </div>
                    <div style="width: 400px; text-align: right;">
                        <el-popover placement="bottom" title="买家WeChat" width="200" trigger="click"
                            :content="transaction.buyer_wechat">
                            <el-button size="small" slot="reference" v-if="transaction.state !== '已取消'">联系买家</el-button>
                        </el-popover>

                        <el-button type="success" size="small" v-if="transaction.state === '待交易'"
                            @click="confirmTransaction(transaction)" style="margin-left: 5px;">确认交易</el-button>
                        <el-button type="danger" size="small"
                            v-if="transaction.state === '已取消' || transaction.state === '已完成'" 
                            style="margin-left: 5px;" @click="deleteTransaction(transaction)">删除订单</el-button>
                    </div>
                </div>

            </el-card>
        </el-main>

        <el-footer>
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNumber" :page-sizes="[100, 200, 300, 400]"
                :page-size="pageSize" layout="total, prev, pager, next" :total="total">
            </el-pagination>
        </el-footer>

    </el-container>
</template>

<script>
export default {
    name:'SoldProducts',
    data() {
        return {
           pageNumber:1,
           pageSize:24,
           total:0,
           transactionState:null,//订单状态
           transactions: [],
           user: JSON.parse(localStorage.getItem('SecondHand-User') || '{}'),//获取当前登录用户
           productName:null,//绑定搜索框

        }
    },
    created() {
        this.load()
    },
    methods: {
        load(){
            this.$request.get('/transaction/selectAllInfoBySellerId',{
                params:{
                    pageNumber: this.pageNumber,
                    pageSize: this.pageSize,
                    userId: this.user.id,
                    transactionState: this.transactionState,
                    productName:this.productName
                }
            }).then(res=>{
                this.transactions = res.data.records
                this.total = res.data.total
            })
        },
        handleCurrentChange(newPage){//绑定分页器
            this.pageNumber=newPage
            // this.load()
        },
        changeState(state) {//绑定状态按钮
            this.pageNumber=1
            this.total=0
            this.transactionState = state
            this.load()
        },
        cancelTransaction(transaction){
            this.$confirm('是否取消交易？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/cancelBySeller',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('取消交易成功')
                            this.load()
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })
        },
        deleteTransaction(transaction){//绑定删除订单按钮
            this.$confirm('是否删除此交易记录？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/sellerDelete',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('删除交易成功')
                            this.load()
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })
        },
        confirmTransaction(transaction){
            this.$confirm('是否完成交易？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/confirmTransaction',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('确认交易成功')
                            this.load()
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })

        },
        showDetail(transactionId){
            this.$router.push({
                name: 'SellerTransactionDetail',
                query: {
                    id: transactionId
                }
            })
        },
        search(){//绑定搜索按钮
            this.load()
        },
        reset(){//绑定重置按钮
            this.productName=null
            this.load()
        }
    }
}
</script>

<style scoped>
.stateButton:hover {
    background-color: #fff !important;
    color: #606266 !important;
}

.stateButton:active {
    background-color: #fff !important;
    color: #606266 !important;
}


.el-button--success {
    background-color: rgb(25, 156, 96);
    border-color: rgb(25, 156, 96);
}

.el-button--success:hover {
    background-color: rgb(31, 173, 109);
    border-color: rgb(31, 173, 109);
}

.el-divider--horizontal {
    margin: 6px 0;
}
</style>