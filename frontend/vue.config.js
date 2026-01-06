module.exports = {
  devServer: {
    port: 8080,
    proxy: {
      '/authCenter': {
        target: 'http://localhost:9091',
        changeOrigin: true,
        pathRewrite: {
          '^/authCenter': ''
        }
      },
      '/systemCenter': {
        target: 'http://localhost:9092',
        changeOrigin: true,
        pathRewrite: {
          '^/systemCenter': ''
        }
      }
    }
  },
  lintOnSave: false
}


