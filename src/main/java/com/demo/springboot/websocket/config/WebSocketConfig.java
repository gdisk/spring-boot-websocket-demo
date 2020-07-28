package com.demo.springboot.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 配置 WebSocket 服务器
 *
 * @author three_stones@foxmail.com
 */
@Configuration
public class WebSocketConfig {
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
		return serverEndpointExporter;
	}
}
