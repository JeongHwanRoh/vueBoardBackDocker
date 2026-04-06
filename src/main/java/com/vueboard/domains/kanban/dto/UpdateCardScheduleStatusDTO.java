package com.vueboard.domains.kanban.dto;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCardScheduleStatusDTO {
	private long cardId;
	private LocalDate predictedStartDate;
	private LocalDate predictedEndDate;
	private LocalDate actualStartDate;
	private LocalDate actualEndDate;
	private String status;

}
