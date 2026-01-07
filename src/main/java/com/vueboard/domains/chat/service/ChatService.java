package com.vueboard.domains.chat.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vueboard.domains.chat.entity.Chat;
import com.vueboard.domains.chat.entity.ChatMessage;
import com.vueboard.domains.chat.mapper.ChatMapper;

@Service
public class ChatService {
	@Autowired
	private ChatMapper chatMapper;
	
    public void saveMessage(ChatMessage message) {
        chatMapper.insertChat(message);
    }
	
}
