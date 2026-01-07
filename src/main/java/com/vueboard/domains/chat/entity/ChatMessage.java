package com.vueboard.domains.chat.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DB 구조와 무관하게, 클라이언트와 데이터를 주고받는 용도의 DTO (비즈니스 로직이 주 목적)


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage {

	private String memberId;
	private String name;
	private String message;
	private LocalDateTime sendtime;
}
