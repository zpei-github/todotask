const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: './',

  transpileDependencies: true,

  devServer: {
    proxy: {
      "/api": {
        target: "http://192.168.10.3:48888/",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
      }
    }
  },

})
