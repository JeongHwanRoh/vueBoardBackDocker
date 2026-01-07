package com.vueboard.domains.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.domains.board.entity.Board;
import com.vueboard.domains.chat.entity.Chat;
import com.vueboard.domains.chat.entity.ChatMessage;

@Mapper
public interface ChatMapper {
	int insertChat(ChatMessage message);
}
