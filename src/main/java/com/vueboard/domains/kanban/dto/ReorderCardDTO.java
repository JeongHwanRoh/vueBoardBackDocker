package com.vueboard.domains.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReorderCardDTO {
	private long cardId;
	private String columnName;
	private long orderNum;
}
