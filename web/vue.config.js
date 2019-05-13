module.exports = {
    //baseUrl: './',
    publicPath : './',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/api':{
                target:'http://localhost:8090',
                changeOrigin:true,
                pathRewrite:{
                    '/api':''
                }
            },
            '/dev':{
                target: 'http://localhost:8090',
                changeOrigin: true
            }
        }
    }
}