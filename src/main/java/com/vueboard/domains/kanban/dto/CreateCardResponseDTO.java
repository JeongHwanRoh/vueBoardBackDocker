package com.vueboard.domains.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCardResponseDTO {
	private long cardId;
	private long columnId;
	private String title;
	private long orderNum;
	private String cardInfo;
	private String classification;
}
