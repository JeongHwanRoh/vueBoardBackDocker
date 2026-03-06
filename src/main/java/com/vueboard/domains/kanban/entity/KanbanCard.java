package com.vueboard.domains.kanban.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class KanbanCard {
	
	private long cardId;
	private String boardId;
	private String title;
	private String cardInfo;
	private String status;
	private long orderNum;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	

}
