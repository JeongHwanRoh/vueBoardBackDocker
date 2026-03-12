package com.vueboard.domains.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatedKanbanCardDTO {
	private long cardId;
	private String columnId;
	private String title;
	private long orderNum;
	private String cardInfo;
}
