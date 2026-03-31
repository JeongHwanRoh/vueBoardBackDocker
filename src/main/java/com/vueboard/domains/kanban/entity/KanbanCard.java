package com.vueboard.domains.kanban.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class KanbanCard {
	
	private long cardId;
	private long columnId;
	private String title;
	private String classification;
	private long orderNum;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	

}