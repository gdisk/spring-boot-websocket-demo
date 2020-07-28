package com.demo.springboot.websocket.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.websocket.util.WebSocketSessionUtil;

/**
 * 演示 controller
 *
 * @author three_stones@foxmail.com
 */
@RestController
public class WebSocketDemoController {
	@GetMapping("/push/{toClientId}")
	public String pushToClient(@PathVariable String toClientId, String message) throws IOException {
		WebSocketSessionUtil.sendMessage(toClientId, message);
		return "消息推送成功";
	}
}