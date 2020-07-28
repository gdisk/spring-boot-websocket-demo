package com.demo.springboot.websocket.server;

import java.io.IOException;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.springboot.websocket.util.WebSocketSessionManager;
import com.demo.springboot.websocket.util.WebSocketSessionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ServerEndpoint("/ws/{clientId}")
public class WebSocketServer {
	/** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
	private Session session = null;
	/** 客户端ID */
	private String clientId = null;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("clientId") String clientId) {
		this.clientId = clientId;
		this.session = session;
		WebSocketSessionManager.addSession(clientId, session);
		log.info("客户端连接:" + clientId + ",当前客户端连接数为:" + WebSocketSessionManager.getOnlineCount());
		try {
			WebSocketSessionUtil.sendMessage(this.clientId, "连接成功");
		} catch (IOException e) {
			log.error("客户端:" + clientId + ",网络异常!!!!!!");
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(@PathParam("clientId") String id) {
		System.out.println(id);
		if (this.session == null || this.clientId == null) {
			log.warn("客户端尚未与服务器建立连接,当前客户端连接数为:" + WebSocketSessionManager.getOnlineCount());
			return;
		}
		WebSocketSessionManager.removeSession(this.clientId);
		log.info("客户端关闭:" + this.clientId + ",当前客户端连接数为:" + WebSocketSessionManager.getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法，这里主要演示一个用户给另一个用户发送消息
	 * 
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("收到客户端消息:" + this.clientId + ",报文内容:" + message);
		JSONObject ret = new JSONObject();
		if (StringUtils.isNotBlank(message)) {
			try {
				// 解析发送的报文
				JSONObject jsonObject = JSON.parseObject(message);
				ret.put("senderId", this.clientId);
				ret.put("msg", jsonObject.getString("msg"));
				String toClientId = jsonObject.getString("receiverId");
				// 传送给对应toClientId客户端的websocket
				if (StringUtils.isNotBlank(toClientId)) {
					Session toClientSession = WebSocketSessionManager.getSession(toClientId);
					if (null == toClientSession) {
						log.error("请求的客户端:" + toClientId + "不在线");
						WebSocketSessionUtil.sendMessage(this.clientId, "对方不在线");
					} else {
						WebSocketSessionUtil.sendMessage(toClientId, ret.toJSONString());
						WebSocketSessionUtil.sendMessage(this.clientId, "消息发送成功");
					}
				} else {
					WebSocketSessionUtil.sendMessage(this.clientId, "接送方客户端不能为空");
				}
			} catch (Exception e) {
				log.error("发送消息失败", e);
			}
		}
	}

	/**
	 * 发生错误时调用的方法
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("客户端错误:" + this.clientId, error);
	}
}