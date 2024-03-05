<template>
    <el-container>
        <el-main>
            <!-- 状态按钮 -->
            <div style="display: flex; margin-bottom: 30px; margin-left: 30px;">
                <div>
                    <el-button class="stateButton" v-if="this.transactionState === null"  round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState(null)">全部({{ page.total }})</el-button>
                    <el-button v-if="this.transactionState !== null" class="stateButton" round
                        style="border:0px;  background-color:rgb(242, 241, 246) ;" @click="changeState(null)">全部</el-button>
                </div>

                <div>
                    <el-button v-if="this.transactionState === '待交易'" class="stateButton" round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState('待交易')">待交易({{ page.total }})</el-button>
                    <el-button v-if="this.transactionState !== '待交易'" class="stateButton" round
                        style="border:0px;  background-color:rgb(242, 241, 246) ;"
                        @click="changeState('待交易')">待交易</el-button>
                </div>

                <div>
                    <el-button v-if="this.transactionState === '待确认'" class="stateButton" round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState('待确认')">待确认({{ page.total }})</el-button>
                    <el-button v-if="this.transactionState !== '待确认'" class="stateButton" round
                        style="border:0px;  background-color:rgb(242, 241, 246) ;"
                        @click="changeState('待确认')">待确认</el-button>
                </div>

                <div>
                    <el-button v-if="this.transactionState === '已完成'" class="stateButton" round
                        style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                        @click="changeState('已完成')">已完成({{ page.total }})</el-button>
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
                        @click="changeState('已取消')">已取消({{ page.total }})</el-button>
                </div>



            </div>

            <el-card :body-style="{ padding: '10px' }" v-for="transaction in transactions" :key="transaction.id"
                style="float: left; margin-left: 30px ; margin-bottom: 30px; width: 500px;" v-if="transaction">

                <!-- 卖家头像和昵称和订单状态-->
                <div style="display: flex; margin-bottom: 5px;">
                    <div style="flex:0;margin-right: 5px;">
                        <el-image :src="transaction.seller_avatar" lazy
                            style="width: 30px; height: 30px; border-radius: 50%; border: 10px;"></el-image>
                    </div>
                    <div style="height: 30px; width:250px;display: flex; align-items: center;font-size: 14px;">{{
                        transaction.seller_nickname }}</div>
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
                        <el-popover placement="bottom" title="卖家WeChat" width="200" trigger="click"
                            :content="transaction.seller_wechat">
                            <el-button size="small" slot="reference" v-if="transaction.state !== '已取消'">联系卖家</el-button>
                        </el-popover>

                        <el-button type="success" size="small" v-if="transaction.state === '待确认'"
                            @click="confirmFinish(transaction)" style="margin-left: 5px;">确认完成交易</el-button>
                        <el-button type="danger" size="small"
                            v-if="transaction.state === '已取消' || transaction.state === '已完成'" 
                            style="margin-left: 5px;" @click="deleteTransaction(transaction)">删除订单</el-button>
                    </div>
                </div>

            </el-card>


        </el-main>

        <!-- 分页器 -->
        <el-footer>
            <el-pagination @current-change="handleCurrentChange" :current-page="page.pageNumber" :page-sizes="[100, 200, 300, 400]"
                :page-size="page.pageSize" layout="total, prev, pager, next" :total="page.total">
            </el-pagination>
        </el-footer>

    </el-container>
</template>

<script>
import { formToJSON } from 'axios'

export default {
    name: 'PurchasedProducts',
    data() {
        return {
            user: JSON.parse(localStorage.getItem('SecondHand-User')),//获取当前登录用户
            transactionState: null,
            transactions: [],
        }
    },
    computed:{
        page(){
            return {
                pageNumber: 1,
                pageSize: 24,
                total: 0,
            }
        }
    },
    created() {
        this.load()
    },
    methods: {
         load() {
            this.transactions = []
            this.$request.get('/transaction/selectAllInfoByBuyerId', {
                params: {
                    pageNumber: this.page.pageNumber,
                    pageSize: this.page.pageSize,
                    userId: this.user.id,
                    transactionState: this.transactionState
                }
            }).then(async res => {
                this.transactions = res.data.records
                this.page.total = res.data.total

            })
        },
        changeState(state) {//绑定状态按钮
            this.page.pageNumber=1
            this.page.total=0
            this.transactionState = state
            this.load()
        },
        handleCurrentChange(newPage) {//绑定页码选择器
            this.page.pageNumber = newPage
            this.load()  
        },
        deleteTransaction(transaction){//绑定删除订单按钮
            this.$confirm('是否确认删除交易？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/buyerDelete',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('删除交易成功')
                            this.load()
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })
        },
        cancelTransaction(transaction){
            this.$confirm('是否确认取消交易？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/cancel',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('取消交易成功')
                            this.load()
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })
        },
        confirmFinish(transaction){
            this.$confirm('是否确认完成交易？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/confirmFinish',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('确认成功')
                            this.load()
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })

        },
        showDetail(transactionId){
            this.$router.push({
                name: 'TransactionDetail',
                query: {
                    id: transactionId
                }
            })
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