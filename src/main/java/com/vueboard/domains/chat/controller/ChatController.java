package com.vueboard.domains.chat.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vueboard.domains.chat.entity.ChatMessage;
import com.vueboard.domains.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Vue 포트 허용(Nuxt vue용)
public class ChatController {
	@Autowired
	private  ChatService chatService; 
	
	@MessageMapping("/send") // 클라이언트 -> 서버
	@SendTo("/topic/messages") // 서버 -> 구독자
	public ChatMessage handleChat(ChatMessage message) {
		// db 저장 전 sendtime 수동 세팅
	    message.setSendtime(LocalDateTime.now());
		 chatService.saveMessage(message);
		System.out.println("💬 Received message: " + message);
		return message;
	}
	
	
}
