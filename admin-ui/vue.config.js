const path = require('path')
const resolve = dir => {
  return path.join(__dirname, dir)
}

// 项目部署基础
 const BASE_URL = process.env.NODE_ENV === 'production' ? 'http://localhost:8081/smarthome/' : '/'

module.exports = {
  publicPath: BASE_URL,
  // 如果你不需要使用eslint，把lintOnSave设为false即可
  lintOnSave: false,
  outputDir: 'target/static',
  chainWebpack: config => {
    config.resolve.alias
      .set('@', resolve('src')) // key,value自行定义，比如.set('@@', resolve('src/components'))
      .set('_c', resolve('src/components'))
  },
  // 打包时不生成.map文件
  productionSourceMap: false,
  // 这里写你调用接口的基础路径，来解决跨域，如果设置了代理，那你本地开发环境的axios的baseUrl要写为 '' ，即空字符串
  devServer: {
    port: 8004,
    proxy: 'http://localhost:8081'
  }
}
