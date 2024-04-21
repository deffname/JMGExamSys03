'use strict'
const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

// All configuration item explanations can be find in https://cli.vuejs.org/config/
module.exports = {
  publicPath: '/',              // 部署应用包时的基本 URL,用法和 webpack 本身的 output.publicPath 一致
  outputDir: 'dist',            // 构建输出目录（打包位置）
  assetsDir: 'static',          // 放置生成的静态资源（js，css，img，fonts）的（相对于outputDir）的目录
  lintOnSave: false,            // 是否校验语法
  productionSourceMap: false,   // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建
  devServer: {
    port: 8888,
    open: true,
  },
  configureWebpack: {           // 绝对路径
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  }
}