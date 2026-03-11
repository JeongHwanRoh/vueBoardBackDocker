package com.vueboard.domains.kanban.entity;



import java.time.LocalDateTime;

import lombok.Data;

@Data
public class KanbanColumns {
	private long columnId;
	private String boardId;
	private String columnName;
	private long orderNum;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
