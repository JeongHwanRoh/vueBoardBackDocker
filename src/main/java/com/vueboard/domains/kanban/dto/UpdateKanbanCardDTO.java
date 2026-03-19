package com.vueboard.domains.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateKanbanCardDTO {
	private long cardId;
	private String columnName;
	private String title;
	private long orderNum;
	private String cardInfo;
}