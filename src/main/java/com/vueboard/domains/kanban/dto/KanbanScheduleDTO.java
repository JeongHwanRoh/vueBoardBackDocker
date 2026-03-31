package com.vueboard.domains.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KanbanScheduleDTO {	
	private long cardId;
	private String title;
	private String classification;
	private String cardInfo;
	private String predictedStartDate;
	private String predictedEndDate;
	private String actualStartDate;
	private String actualEndDate;
	private String status;

}
