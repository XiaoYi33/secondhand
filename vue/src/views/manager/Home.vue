<template>
    <el-container v-if="products">
        <el-main >
            <el-card :body-style="{ padding: '0px' }" v-for="product in products" :key="product.id"
                style="float: left; margin-left: 30px ; margin-bottom: 30px; ">
                <el-image :src="product.image" lazy style="width: 250px; height: 250px;"></el-image>
                <div style="padding: 14px; width: 250px; ">
                    <div
                        style="height: 29px; text-overflow: ellipsis; overflow: hidden; font-size: 18px; font-weight: bold;">
                        {{ product.name
                        }}
                    </div>
                    <div class="bottom clearfix">
                        <span style="font-size: 12px; color: rgb(244, 73, 61); ">¥</span>
                        <span style="font-size: 14px; color: rgb(244, 73, 61); font-weight: bold;">{{ product.price
                        }}</span>
                        <el-button type="text" class="button" style="color: rgb(25,156,96)"
                            @click="check(product.id)">查看</el-button>
                    </div>
                    <hr style="border-color: rgb(242,241,246);">
                    <div style="padding-top: 1%;display: flex;">
                        <div style="width: 50px;">
                            <el-image :src="product.user.avatar" lazy
                                style="width: 40px; height: 40px; border-radius: 50%; border: 10px;"></el-image>
                        </div>
                        <div>
                            <div style="height: 50%; font-size: 12px; color:rgb(122,122,122) ;">{{product.user.nickname}}</div>
                            <div style="height: 50%; font-size: 12px; color: rgb(190,190,190);">发布于{{product.updateTime}}</div>
                        </div>
                    </div>
                </div>
            </el-card>

        </el-main>
        <el-footer>
            <el-pagination @current-change="handleCurrentChange" :current-page="pageNumber" :page-size="pageSize"
                layout="total, prev, pager, next" :total="total">
            </el-pagination>
        </el-footer>


        <!-- 发布商品按钮 -->
        <el-button type="success" class="floating-button" icon="el-icon-plus" @click="publishDialogVisible = true"
            round>发布</el-button>

        <!-- 发布商品对话框 -->
        <el-dialog title="发布商品" :visible.sync="publishDialogVisible" width="35%">
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
                        :on-success="handleImageSuccess" :auto-upload="false" ref="upload">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5mb</div>
                    </el-upload>
                </el-form-item>
                <div style="text-align: center; ">
                    <el-button type="success" @click="publishButtom">发布</el-button>
                    <el-button type="danger" @click="cancel">取消</el-button>
                </div>
            </el-form>
        </el-dialog>
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
            publishDialogVisible: false,//绑定发布商品对话框
            form: {},
            user: JSON.parse(localStorage.getItem('SecondHand-User')),//获取当前登录用户
            rules: {//发布商品校验
                name: [
                    { required: true, message: '请输入昵称', trigger: 'blur' },
                ],
                categoryId: [
                    { required: true, message: '请选择商品类别', trigger: 'blur' },
                ],
                description: [
                    { required: true, message: '请输入商品描述', trigger: 'blur' },
                ],
                price: [
                    { required: true, message: '请输入商品价格', trigger: 'blur' },
                ],

            },

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
        handleCurrentChange(pageNumber) {//绑定页码选择器
            this.pageNumber = pageNumber
            this.load()
        },
        publishButtom() {//绑定发布按钮
            this.form.userId = this.user.id,
                this.$refs.upload.submit()//表单验证成功后才上传图片


            this.$refs.formRef.validate((valid) => {
                setTimeout(() => {
                    if (valid) {
                        if (this.form.image) {
                            this.$request.post('/product/add', this.form).then(res => {
                                if (res.code === '200') {
                                    this.$message.success('发布成功')
                                    this.load()
                                    this.form = {}
                                    this.publishDialogVisible = false
                                } else {
                                    this.$message.error(res.msg)
                                }
                            })
                        } else {
                            this.$message.error('请上传图片或等待图片上传完')
                        }

                    } else {
                        this.$message.error('请检查是否输入完整')
                    }
                }, 1000)//等1秒后才执行
            })

        },
        cancel() {//绑定取消按钮
            this.form = {},
                this.publishDialogVisible = false
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
        check(formId) {//绑定商品查看按钮
            this.$router.push({
                name: 'ProductDetail',
                query: {
                    id: formId
                }
            })
        }
    },
}



</script>


<style scoped>
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

.floating-button {
    position: fixed;
    bottom: 8%;
    right: 4%;
    z-index: 999;
    /* height: 50px;
    width: 50px; */
    box-shadow: 1px 1px 6px rgb(242, 241, 246);

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