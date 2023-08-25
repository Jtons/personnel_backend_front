const { createProxyMiddleware } = require('http-proxy-middleware');
module.exports = function(app) {
  app.use(
    '/base',
    createProxyMiddleware({
      target: 'http://127.0.0.1:9002', // 服务器地址
      changeOrigin: true, // 改变起源,
      pathRewrite: { '^/base': '' }
    })
  );
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://8.130.68.133:9001', // 服务器地址
      changeOrigin: true, // 改变起源,
      pathRewrite: { '^/api': '' }
    })
  );
};
