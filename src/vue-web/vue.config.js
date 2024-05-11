const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: './',

  transpileDependencies: true,

  devServer: {
    proxy: {
      "/api": {
        target: "http://192.168.10.4:28080/",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
      }
    }
  },

})
