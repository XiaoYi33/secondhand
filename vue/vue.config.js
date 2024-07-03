const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
      port:9091
  },
  chainWebpack: config =>{
    config.plugin('html')
        .tap(args => {
          args[0].title = "广应科二手易物平台";
          return args;
        })
  }
})
