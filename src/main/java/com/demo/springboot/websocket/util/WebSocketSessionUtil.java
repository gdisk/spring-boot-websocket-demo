package com.demo.springboot.websocket.util;

import java.io.IOException;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket 会话工具类，用于发送消息等
 *
 * @author three_stones@foxmail.com
 */
@Slf4j
public class WebSocketSessionUtil {

	/**
	 * 发送消息到客户端
	 */
	public static void sendMessage(String clientId, String message) throws IOException {
		if (StringUtils.isBlank(clientId)) {
			log.error("客户端ID为空");
			return;
		}
		Session session = WebSocketSessionManager.getSession(clientId);
		if (null == session) {
			log.error("请求的客户端:" + clientId + "不在线");
		} else {
			session.getBasicRemote().sendText(message);
			log.info("发送消息到:" + clientId + "，报文:" + message);
		}
	}
}
