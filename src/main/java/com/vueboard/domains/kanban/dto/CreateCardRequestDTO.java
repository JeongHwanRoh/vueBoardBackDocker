package com.vueboard.domains.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCardRequestDTO {
    private String title;
    private String cardInfo;
    private String columnName;
    private Integer orderNum;
}
