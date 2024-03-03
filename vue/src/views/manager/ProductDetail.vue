<template>
    <div v-if="form.name" style="display: flex; height:600px; padding: 0px; border-radius: 20px;">

        <!-- 左边：详情 -->
        <div
            style="flex: 1; padding: 10px 20px 0 10px; box-shadow: 0 3px 12px 0 rgba(0,0,0,.1) !important; background-color: #fff;">
            <!-- 左边：返回按钮+商品名 -->
            <div>
                <el-button class="button" icon="el-icon-arrow-left" round
                    style="border:0px; height: 50px; margin-right: 5px;" @click="goback">返回</el-button><span
                    style="font-weight: bold;">{{ form.name }}</span>
            </div>

            <!-- 左边：头像+昵称+时间 -->
            <div style="padding: 10px 0 0 20px; display:flex;align-items: center">
                <el-image v-if="form.user_avatar" :src="form.user_avatar"
                    style="width: 60px; height: 60px; border-radius: 50%; border: 10px; margin-right: 10px;"></el-image>
                <div style="width: 100%;">
                    <div style="font-size: 20px; ;margin-bottom: 5px;">{{ form.user_nickname }}</div>
                    <div style="color:rgb(122,122,122);height: 50%; ;">
                        <span style="margin-right: 1%;">发布于 {{ form.update_time }}</span>
                        <span v-if="form.update_time">/</span>
                        <span v-if="form.update_time" style="margin-left: 1%;">最近更新 {{ form.update_time
                        }}</span>
                    </div>
                </div>
            </div>

            <!-- 左边：价格 -->
            <div style="margin-top: 5px; padding: 10px 0 0 20px;">
                <span style="font-size: 20px; color: rgb(244, 73, 61); ">¥</span>
                <span style="font-size: 30px; color: rgb(244, 73, 61); font-weight: bold;">{{ form.price
                }}</span>
            </div>

            <!-- 左边：类别 -->
            <div style="margin-top: 5px; padding: 12px 0 0 20px; color:rgb(122,122,122);">
                类别：{{ form.category_name }}
            </div>

            <!-- 左边：描述 -->
            <div style="margin-top: 5px; padding: 12px 0 0 20px; height: 50%; font-size: 16px;">
                {{ form.description }}
            </div>



            <!-- 左边：购买按钮 -->
            <div style=" padding: 10px 0 0 20px;margin-top: 10px;">
                <el-button type="success" @click="buy" v-if="form.state === '上架'">我想要</el-button>
            </div>

        </div>


        <!-- 右边：商品图片 -->
        <div style="flex:1; background-color: rgb(242, 241, 246);">
            <el-image v-if="form.image" :src="form.image" style="height: 600px;" fit="fill"
                :preview-src-list="[form.image]"></el-image>
        </div>


    </div>
</template>

<script>
export default {
    name: 'ProductDetail',
    data() {
        //数据
        return {
            form: {},
            user: JSON.parse(localStorage.getItem('SecondHand-User')),
            transaction: {},
        }
    },
    mounted() {
        this.$request.get('/product/selectProductDetailById/' + this.$route.query.id).then(res => {//获取前端返回的query里的id
            this.form = res.data
            this.transaction = {
                buyerId: this.user.id,
                productId: this.form.id,
                sellerUsername:this.form.user_username
            }
        })
    },
    create() {

    },
    methods: {
        goback() {
            this.$router.back();
        },
        buy() {
            this.$confirm('是否确认购买？<br>购买后请及时联系卖家（联系方式可在订单界面查询）', '确认购买', { type: "warning",dangerouslyUseHTMLString: true}).then(res => {
                this.$request.post('/transaction/create', this.transaction).then(res => {
                    if (res.code === '200') {
                        this.$message.success('购买成功，请及时联系卖家（联系方式可在订单界面查询）')
                        // setTimeout(() => { this.$router.back() }, 2000)
                        this.$router.back()
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })
        }
    }
}
</script>

<style scoped>
.el-button--success {
    background-color: rgb(25, 156, 96);
    border-color: rgb(25, 156, 96);
}

.el-button--success:hover {
    background-color: rgb(31, 173, 109);
    border-color: rgb(31, 173, 109);
}

.el-card {
    box-shadow: none;
}

.button:hover {
    background-color: rgba(0, 0, 0, .1);
    color: #fff;
}</style>