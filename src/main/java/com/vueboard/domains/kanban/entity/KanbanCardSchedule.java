package com.vueboard.domains.kanban.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class KanbanCardSchedule {
	private long cardId;
	private LocalDateTime predictedStartDate;
	private LocalDateTime predictedEndDate;
	private LocalDateTime actualStartDate;
	private LocalDateTime actualEndDate;
	
}
