<template>
    <div>
        <el-card :body-style="{ padding: '0px' }">
            <div style="display: flex; height: 80vh;">
                <div style="flex: 1;">
                    {{ form.name }}
                    {{ form.user.username }}
                </div>
                <div style="flex:1">
                    <el-image v-if="form.image" :src="form.image" fit="fill" :preview-src-list="[form.image]"></el-image>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: 'ProductDetail',
    data() {
        //数据
        return {
            form: {
                name:'',
                user:{
                    username:''
                },
                image:''
                
            }
        }
    },
    mounted() {
        this.$request.get('/product/selectById/' + this.$route.query.id).then(res => {//获取前端返回的query里的id
                this.form = res.data
            })
    },
    create(){

    },
    methods: {

    }
}
</script>

<style scoped></style>