package com.vueboard.domains.kanban.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class KanbanBoard {

	private String boardId;
	private Long pn;
	private String boardName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}