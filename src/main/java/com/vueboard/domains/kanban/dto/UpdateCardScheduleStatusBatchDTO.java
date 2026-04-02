package com.vueboard.domains.kanban.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCardScheduleStatusBatchDTO {
	// 프론트의 { schedules: [...] } 구조와 매핑
	private List<UpdateCardScheduleStatusDTO> schedules;
}
