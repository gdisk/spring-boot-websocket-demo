package com.demo.springboot.websocket.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.Session;

/**
 * WebSocket 会话管理
 *
 * @author three_stones@foxmail.com
 */
public class WebSocketSessionManager {
	/**所有seesion存放到ConcurrentHashMap中，分布式部署时可以换成存放在数据库或缓存中*/
	private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap();
	private static AtomicInteger onlineCount = new AtomicInteger(0);

	public static void addSession(String clientId, Session session) {
		if (sessionMap.containsKey(clientId)) {
			sessionMap.remove(clientId);
			sessionMap.put(clientId, session);
		} else {
			sessionMap.put(clientId, session);
			onlineCount.incrementAndGet();
		}
	}

	public static void removeSession(String clientId) {
		if (sessionMap.containsKey(clientId)) {
			sessionMap.remove(clientId);
			onlineCount.decrementAndGet();
		}
	}

	public static Session getSession(String clientId) {
		return sessionMap.get(clientId);
	}

	public static Integer getOnlineCount() {
		return onlineCount.get();
	}
}