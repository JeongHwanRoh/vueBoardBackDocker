package com.vueboard.domains.kanban.dto;

import java.time.LocalDateTime;

import com.vueboard.domains.kanban.entity.KanbanCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCardDTO {	
    private String title;
    private String cardInfo;
    private String columnName;
    private Integer orderNum;
	
}