package com.vueboard.domains.kanban.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KanbanColumnDTO {
    private String columnName;
    private Integer orderNum;
    private Long cardId;
    private String title;
    private String cardInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
