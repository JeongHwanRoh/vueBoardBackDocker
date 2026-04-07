package com.vueboard.domains.kanban.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CreateCardResponseDTO {
	private long cardId;
	private long columnId;
	private String title;
	private long orderNum;
	private String cardInfo;
	private String classification;
}
