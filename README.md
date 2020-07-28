# springboot整合websocket示例

## WebSocket介绍
WebSocket 协议在2008年诞生，2011年成为国际标准。所有支持HTML5的浏览器都已经支持了。WebSocket 使得客户端和服务器之间的数据交换变得更加简单，在 WebSocket API 中，浏览器和服务器只需要完成一次握手，然后浏览器和服务器之间就形成了一条快速通道，两者之间就直接可以创建持久性的连接，并进行双向数据传输。
## WebSocket特点
- **双向数据传输**

  WebSocket 的最大特点就是，服务器可以主动向客户端推送信息，客户端也可以主动向服务器发送信息，是真正的双向平等对话，就像Socket一样。WebSocket 协议，能更好的节省服务器资源和带宽，并且能够更实时地进行通讯。
  
- **建立在 TCP 协议之上**

  WebSocket 协议是建立在TCP协议上的应用层协议，服务器端的实现比较容易
  
- **与 HTTP 协议有着良好的兼容性**

  默认端口也是80和443，并且握手阶段采用 HTTP 协议，因此握手时不容易屏蔽，能通过各种 HTTP 代理服务器，如Nginx等
  
- **数据格式比较轻量，性能开销小，通信高效**

  Websocket协议通过第一个请求建立了TCP连接之后，之后交换的数据都不需要发送 HTTP header就能交换数据。每次数据交换除了真正的数据部分外，服务器和客户端不需要大量交换HTTP header，提高信息交换效率。
  
- **可以发送文本，也可以发送二进制数据**

- **没有同源限制**

  客户端可以与任意服务器通信，不存在跨域问题
  
- **协议标识符是`ws`**

  WebSocket 协议标识符是`ws`，加密的协议标识符为`wss`
  
## Springboot整合Websocket

运行`Application.java`，然后浏览器打开[localhost:8080](localhost:8080)

整合后示例运行效果如下：
![web-demo](https://blog010.oss-cn-beijing.aliyuncs.com/img/20200728151852.gif)
