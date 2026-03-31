package com.vueboard.domains.kanban.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// column + card 조회 응답용 DTO
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KanbanColumnDTO {
    private Long columnId;   // 추가: 프론트에서 update 요청 시 사용
    private String columnName;
    private Integer orderNum;
    private Long cardId;
    private String title;
    private String cardInfo;
    private String classification;
    private LocalDate predictedStartDate;
    private LocalDate predictedEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
