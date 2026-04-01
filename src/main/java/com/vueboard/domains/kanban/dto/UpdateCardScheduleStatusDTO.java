package com.vueboard.domains.kanban.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCardScheduleStatusDTO {
	private long cardId;
	private String actualStartDate;
	private String actualEndDate;
	private String status;

}
