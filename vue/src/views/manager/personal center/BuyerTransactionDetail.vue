<template>
    <div v-if="form.state" style="display: flex; height:600px; padding: 0px;">
        <!-- 左边 -->
        <div
            style="flex: 1; padding: 10px 10px 0 10px; box-shadow: 0 3px 12px 0 rgba(0,0,0,.1) !important; background-color: #fff;">
            <!-- 返回按钮+商品名 -->
            <div>
                <el-button class="button" icon="el-icon-arrow-left" round
                    style="border:0px; height: 50px; margin-right: 5px;" @click="goback">返回</el-button><span
                    style="font-weight: bold;">{{ form.comment }}</span>
            </div>

            <!-- 进度条 -->
            <div  style="padding: 0 100px 0 100px; height: 57px;">
                <el-steps v-if="form.state !== '已取消'" :active="active" finish-status="success" align-center>
                    <el-step title="交易"></el-step>
                    <el-step title="确认"></el-step>
                    <el-step title="完成"></el-step>
                </el-steps>
            </div>

            <!-- 分割线 -->
            <div style="padding: 10px 20px 0 20px;">
                <el-divider></el-divider>
            </div>

            <!-- 商品图片、名称、价格 -->
            <div @click="check(form.product_id)">
                <!-- 商品信息 -->
                <div style="display: flex;padding: 14px 20px 0 20px;">

                    <!-- 左边：商品图片 -->
                    <div style="flex:0">
                        <el-image :src="form.product_image" lazy
                            style="width: 100px; height: 100px; border-radius: 4%;"></el-image>
                    </div>

                    <!-- 右边：商品名称和价格 -->
                    <div style="flex:content;padding-left: 20px;">
                        <!-- 第一行：商品名称和商品价格 -->
                        <div style="display: flex;">
                            <div style="flex:content; ">
                                <span
                                    style="display: flex; height: 20px; align-items: end;font-size: 16px; font-weight: bold;">{{
                                        form.product_name }}</span>
                            </div>
                            <div style="flex:0;display: flex; height: 20px; align-items: end;">
                                <span style="font-size: 12px;  ">¥</span>
                                <span style="font-size: 16px;  font-weight: bold;">{{
                                    form.price }}</span>
                            </div>
                        </div>
                        <!-- 第二行：商品描述 -->
                        <div style="padding-top: 10px;">
                            {{ form.product_description }}
                        </div>
                    </div>




                </div>
                <!-- 订单金额 -->
                <div></div>
            </div>

            <!-- 订单金额 -->
            <div style="display: flex; padding: 10px 20px 0 20px;">
                <div style="flex: content;">订单金额</div>
                <div style="flex: 0;">
                    <span style="font-size: 12px; color: rgb(244, 73, 61); ">¥</span>
                    <span style="font-size: 16px; color: rgb(244, 73, 61); font-weight: bold;">{{ form.price
                    }}</span>
                </div>
            </div>

            <!-- 分割线 -->
            <div style="padding: 10px 20px 0 20px;">
                <el-divider></el-divider>
            </div>

            <!-- 其他信息 -->
            <div style=" padding: 10px 20px 0 20px; height: 200px;" >
                <div style="flex:content;display: flex; margin-bottom: 10px;">
                    <div style="width: 100px;">交易编号</div>
                    <div style="flex: content;">{{ form.id }}</div>
                </div>
                <div style="flex:content;display: flex;margin-bottom: 10px;">
                    <div style="width: 100px;">卖家昵称</div>
                    <div style="flex: content;">{{ form.seller_nickname }}</div>
                </div>
                <div v-if="form.state!=='已取消'" style="flex:content;display: flex;margin-bottom: 10px;">
                    <div style="width: 100px;">卖家WeChat</div>
                    <div style="flex: content;">{{ form.seller_wechat }}</div>
                </div>
                <div style="flex:content;display: flex;margin-bottom: 10px;">
                    <div style="width: 100px;">交易创建时间</div>
                    <div style="flex: content;">{{ form.create_time }}</div>
                </div>
                <div style="flex:content;display: flex;margin-bottom: 10px;">
                    <div style="width: 100px;" v-if="form.state === '已完成'">交易完成时间</div>
                    <div style="width: 100px;" v-if="form.state === '已取消'">交易取消时间</div>
                    <div style="flex: content;">{{ form.update_time }}</div>
                </div>
            </div>

            <div style="padding: 10px 20px 0 20px; ">
                <el-divider></el-divider>
            </div>

            <div style="padding: 10px 20px 0 20px; display: flex;">

                <div style="flex:content">
                    <el-button type="text" style="color:rgb(122,122,122); font-size: 13px; height: 30px;"
                        v-if="form.state === '待交易'" @click="cancelTransaction(form)">取消订单</el-button>
                
                </div>
                <div style="flex:0">
                    <el-button type="success" size="small" v-if="form.state === '待确认'"
                        @click="finishTransaction(form)">完成交易</el-button>
                    <el-button type="danger" size="small" v-if="form.state === '已取消' || form.state === '已完成'"
                        @click="deleteTransaction(form)">删除订单</el-button>
                </div>



            </div>

        </div>

        <!-- 右边，空着占位 -->
        <div style="flex: 1;"></div>
    </div>
</template>

<script>
export default {
    name: 'TransactionDetail',
    data() {
        return {
            form: {},//
        }
    },
    computed: {
        active() {
            switch (this.form.state) {
                case '待交易': return 0;
                case '待确认': return 1;
                case '已完成': return 3;
            }
        },
    },
    mounted() {
        this.$request.get('/transaction/selectDetailById/' + this.$route.query.id).then(res => {
            this.form = res.data
        })
    },
    methods: {
        goback() {//绑定返回按钮
            this.$router.back();
        },
        check(formId) {//绑定卡片，点击查看商品
            this.$router.push({
                name: 'ProductDetail',
                query: {
                    id: formId
                }
            })
        },
        deleteTransaction(transaction){//绑定删除订单按钮
            this.$confirm('是否删除此交易记录？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/buyerDelete',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('删除交易成功')
                            this.$router.back();
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })
        },
        cancelTransaction(transaction){
            this.$confirm('是否取消交易？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/cancelByBuyer',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('取消交易成功')
                            location.reload();
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })
        },
        finishTransaction(transaction){
            this.$confirm('是否完成交易？', '', { type: "warning", dangerouslyUseHTMLString: true }).then(res => {
                this.$request.put('/transaction/finishTransaction',transaction).then(res=>{
                    if (res.code === '200') {
                            this.$message.success('完成交易成功')
                            location.reload();
                    } else {
                            this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })

        },
    }
}
</script>

<style scoped>
.button:hover {
    background-color: rgba(0, 0, 0, .1);
    color: #fff;
}

.el-divider--horizontal {
    margin: 6px 0;
}
.el-button--success {
    background-color: rgb(25, 156, 96);
    border-color: rgb(25, 156, 96);
}

.el-button--success:hover {
    background-color: rgb(31, 173, 109);
    border-color: rgb(31, 173, 109);
}
</style>