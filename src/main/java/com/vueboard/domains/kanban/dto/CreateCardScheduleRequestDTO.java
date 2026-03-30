package com.vueboard.domains.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCardScheduleRequestDTO {
	private long cardId;
	private LocalDate predictedStartDate;
	private LocalDate predictedEndDate;
}