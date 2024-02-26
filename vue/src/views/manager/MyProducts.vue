<template>
    <el-container v-if="products !== null">

        <!-- 展示卡片 -->
        <el-main>
            <!-- 状态按钮 -->
            <div style="display: flex; margin-bottom: 30px; margin-left: 30px;">
                <el-button v-if="this.productState === '上架'" class="button" round
                    style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                    @click="changeState('上架')">在卖({{ this.total }})</el-button>
                <el-button v-if="this.productState === '上架'" class="button" round
                    style="border:0px;  background-color:rgb(242, 241, 246) ;" @click="changeState('下架')">已下架</el-button>
                <el-button v-if="this.productState === '下架'" class="button" round
                    style="border:0px;  background-color:rgb(242, 241, 246) ;" @click="changeState('上架')">在卖</el-button>
                <el-button v-if="this.productState === '下架'" class="button" round
                    style="border:0px; font-weight: bold; background-color: #fff;color: #606266;"
                    @click="changeState('下架')">已下架({{ this.total }})</el-button>

            </div>

            <el-card :body-style="{ padding: '0px' }" v-for="product in products" :key="product.id"
                style="float: left; margin-left: 30px ; margin-bottom: 30px;" v-if="products">
                <div style="display: flex;">
                    <!-- 左边图片 -->
                    <el-image :src="product.image" style="height: 120px; width: 120px;"></el-image>
                    <div style="width: 450px; display: flex;">
                        <!-- 中间详情 -->
                        <div style="width: 400px; height: 120px; padding: 10px 0 15px 15px;">
                            <div
                                style="height: 29px; text-overflow: ellipsis; overflow: hidden; font-size: 25px; font-weight: bold; margin-bottom: 10px;">
                                {{ product.name }}</div>
                            <div>
                                <span style="font-size: 12px; color: rgb(244, 73, 61); ">¥</span>
                                <span style="font-size: 20px; color: rgb(244, 73, 61); font-weight: bold;">{{ product.price
                                }}</span>
                                <span style="margin-left: 10px; color:rgb(122,122,122); font-size: 12px;">{{
                                    product.category.name }}</span>
                            </div>
                            <div style="font-size: 12px; margin-top: 22px; color:rgb(122,122,122)"
                                v-if="productState === '上架'">
                                <span style="margin-right: 1%;">发布 {{ product.updateTime }}</span>
                                <span v-if="product.updateTime">/</span>
                                <span v-if="product.updateTime" style="margin-left: 1%;">更新 {{ product.updateTime
                                }}</span>
                            </div>
                            <div style="font-size: 12px; margin-top: 22px; color:rgb(122,122,122)"
                                v-if="productState === '下架'">
                                <span style="margin-right: 1%;">下架原因： {{ product.reason }}</span>
                            </div>
                        </div>

                        <!-- 右边按钮 -->
                        <div style="width: 50px;">
                            <div style="width: 50px; height: 39px;">
                                <el-button type="text"
                                    style="padding-right: 20px; float: right; color: rgb(25,156,96);" v-if="productState === '上架'" @click="editProduct(product.id)">编辑</el-button>
                            </div>
                            <div style="width: 50px; height: 39px;">
                                <el-popconfirm title="确定下架吗？" @confirm="takeDown(product.id)">
                                    <el-button slot="reference" type="text"
                                        style="padding-right: 20px; float: right; color: rgb(221, 173, 68);" v-if="productState === '上架'">下架</el-button>
                                </el-popconfirm>
                            </div>
                            <div style="width: 50px; height: 39px;">
                                <el-popconfirm title="确定删除吗？" @confirm="deleteProduct(product.id)">
                                    <el-button slot="reference" type="text"
                                        style="padding-right: 20px; float: right; color: rgb(240, 37, 37);">删除</el-button>
                                </el-popconfirm>
                            </div>

                        </div>



                    </div>
                </div>

            </el-card>
        </el-main>

       <!-- 编辑商品对话框 -->
       <el-dialog title="发布商品" :visible.sync="editFormVisable" width="35%">
            <el-form :model="form" style="padding-right: 20px;" label-width="80px" :rules="rules" ref="formRef">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="form.name" maxlength="12" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="类别" prop="categoryId">
                    <el-select v-model="form.categoryId">
                        <el-option label="数码" :value="1"></el-option>
                        <el-option label="电器" :value="2"></el-option>
                        <el-option label="家具" :value="3"></el-option>
                        <el-option label="书籍" :value="4"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input v-model="form.description" type="textarea" maxlength="400" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model="form.price"></el-input>
                </el-form-item>
                <el-form-item label="图片" prop="image">
                    <el-upload class="upload-demo" action="http://localhost:9090/file/upload"
                        :headers="{ token: user.token }" :before-upload="beforeImageUpload" :limit="1"
                        :on-success="handleImageSuccess" >
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5mb</div>
                    </el-upload>
                </el-form-item>
                <div style="text-align: center; ">
                    <el-button type="success" @click="save">保存</el-button>
                    <el-button type="danger" @click="cancel">取消</el-button>
                </div>
            </el-form>
        </el-dialog>


        <!-- 分页器 -->
        <el-footer>
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNumber" :page-size="pageSize"
                layout="total, prev, pager, next" :total="total">
            </el-pagination>
        </el-footer>
    </el-container>
</template>

<script>
export default {
    name: 'MyProducts',
    data() {
        return {
            products: [],
            pageNumber: 1,
            pageSize: 24,
            total: 0,
            user: JSON.parse(localStorage.getItem('SecondHand-User')),//获取当前登录用户
            productState: '上架',//绑定
            popconfirmVisable: false,
            form:{},
            editFormVisable:false,//绑定编辑商品弹窗
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.total = 0,
                this.products = [],
                this.$request.get('/product/selectProductsByUserId', {
                    params: {
                        pageNumber: this.pageNumber,
                        pageSize: this.pageSize,
                        userId: this.user.id,
                        productState: this.productState

                    }
                }).then(res => {
                    this.products = res.data.records
                    this.total = res.data.total
                })
        },
        handleCurrentChange(pageNumber) {//绑定页码选择器
            this.pageNumber = pageNumber
            this.load()
        },
        changeState(productState) {//绑定在卖和已下架按钮
            this.productState = productState
            this.load()
        },
        takeDown(id) {
            this.$request.put('/product/takeDown', null, {
                params: { productId: id }
            }).then(res => {
                if (res.code === '200') {
                    this.$message.success('下架成功')
                    this.load()
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        deleteProduct(id) {
            this.$request.delete('/product/delete/' + id).then(res => {
                if (res.code === '200') {
                    this.$message.success('删除成功')
                    this.load()
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        editProduct(id){//绑定编辑商品按钮
            this.editFormVisable=true
            this.form={}
            this.$request.get('/product/selectById/'+id).then(res=>{
                this.form=res.data
            })
        },
        cancel(){//绑定编辑商品对话框取消按钮
            this.form={}
            this.editFormVisable=false
        },
        save(){
            this.$request.put('/product/update',this.form).then(res=>{
                if(res.code==='200'){
                    this.$message.success('保存成功')
                    this.editFormVisable=false
                    this.load()
                }else{
                    this.$message.error(res.msg)
                }
            })
        },
        handleImageSuccess(response, files, fileList) {//绑定上传图片按钮
            this.form.image = response.data

        },
        beforeImageUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
            const isLt5M = file.size / 1024 / 1024 < 5;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt5M) {
                this.$message.error('上传头像图片大小不能超过 5MB!');
            }
            return isJPG && isLt5M;
        },

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