<template>
    <el-container>
        <el-main>
            <el-card :body-style="{ padding: '0px' }" v-for="product in products" :key="product.id"
                style="float: left; margin-left: 30px ; margin-bottom: 30px; ">
                <img :src="product.image" class="image">
                <div style="padding: 14px; width: 250px; ">
                    <div style="height: 23px; text-overflow: ellipsis; overflow: hidden; font-size: 15px;">{{product.name }}
                    </div>
                    <div class="bottom clearfix">
                        <time class="time">{{ product.updateTime }}</time>
                        <el-button type="text" class="button" style="color: rgb(25,156,96)">查看</el-button>
                    </div>
                </div>
            </el-card>

        </el-main>
        <el-footer>
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNumber"
                :page-sizes="[100, 200, 300, 400]" :page-size="pageSize" layout="total, prev, pager, next" :total="total">
            </el-pagination>
        </el-footer>

    </el-container>
</template>



<script>

export default {
    data() {
        return {
            products: [],
            pageNumber: 1,
            pageSize: 24,//一页显示24个商品
            total: 0,
            
        };
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.$request.get('/product/selectByPage', {
                params: {
                    pageNumber: this.pageNumber,
                    pageSize: this.pageSize,
                }
            }).then(res => {
                this.products = res.data.records,
                    this.total = res.data.total
            })
        },
        handleCurrentChange(pageNumber) {
            this.pageNumber = pageNumber
            this.load()
        },
    },
}



</script>
<style scoped>
.time {
    font-size: 13px;
    color: #999;
}

.bottom {
    margin-top: 5px;
    line-height: 12px;
}

.button {
    padding: 0;
    float: right;
}

.image {
    height: 250px;
    width: 250px;
    /* width: 100%; */
    display: block;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}
</style>