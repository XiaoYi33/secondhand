
    <template>
      <div>
        <!-- 触发弹窗的按钮 -->
        <el-button type="primary" @click="dialogVisible = true">点击打开弹窗</el-button>
        
        <!-- 图片弹窗 -->
        <el-dialog
          title="图片展示"
          :visible.sync="dialogVisible"
          width="30%"
          :before-close="handleClose" >
          <!-- 图片内容 -->
          <!-- <img :src="imageUrl" alt="图片展示" style="display: block; max-width: 100%; margin: 0 auto;"> -->
          <el-input v-model="inputCode" placeholder="请输入验证码"></el-input>
          <div style="flex: 1; height: 36px;" >
                <valid-code @update:value="getCode"></valid-code>
              </div>
        </el-dialog>
      </div>
    </template>
   
  <script>
  import ValidCode from "@/components/ValidCode";
  export default {
    
    data() {

        //验证码校验
     const validateCode = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入验证码'))
        } else if(value.toLowerCase() !== this.code){
          callback(new Error('验证码错误'))
        }else{
          callback()
        }
    }//这一串直接从官网拿的

      return {
        code:'',
        inputCode:'',
        dialogVisible: false,
        imageUrl: 'http://example.com/path/to/image.jpg' // 要显示的图片地址
      };
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },

      getCode(code){
      console.log("生成的验证码:"+code)
      this.code=code.toLowerCase()
      console.log("小写转化后的验证码："+this.code)
    },
    }
  };
  </script>