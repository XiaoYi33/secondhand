<template>
        <div v-if="form.name" style="display: flex; height:600px; padding: 0px; border-radius: 20px;">

            <!-- 左边：详情 -->
            <div style="flex: 1; padding: 10px 20px 0 10px; box-shadow: 0 3px 12px 0 rgba(0,0,0,.1) !important; background-color: #fff;">
                <!-- 左边：返回按钮+商品名 -->
                <div>
                    <el-button class="button" icon="el-icon-arrow-left" round style="border:0px; height: 50px; margin-right: 5px;"
                        @click="goback">返回</el-button><span style="font-weight: bold;">{{ form.name }}</span>
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
                    <el-button type="success">我想要</el-button>
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
            form: {

            }
        }
    },
    mounted() {
        this.$request.get('/product/selectProductDetailById/' + this.$route.query.id).then(res => {//获取前端返回的query里的id
            this.form = res.data
        })
    },
    create() {

    },
    methods: {
        goback() {
            this.$router.back();
        },
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
.el-card{
    box-shadow: none;
}
.button:hover{
    background-color: rgba(0,0,0,.1);
    color: #fff;
}
</style>