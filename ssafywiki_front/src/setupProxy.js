const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = (app) => {
  app.use(
    "ws",
    createProxyMiddleware({
      target: process.env.REACT_APP_SERVER_API_URL,
      ws: true, 
    })
  );
};