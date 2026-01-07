package com.vueboard.domains.chat.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

// DB의 CHAT 테이블과 1:1 매핑되는 객체 -> 비즈니스 로직보다는 데이터 영속성이 주목적 

@NoArgsConstructor
@Data
public class Chat {

	private long chatId;
	private String memberId;
	private String name;
	private String message;
	private LocalDateTime sendtime;
}
