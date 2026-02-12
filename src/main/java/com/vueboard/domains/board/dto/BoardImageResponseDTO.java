package com.vueboard.domains.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardImageResponseDTO {
	private Long boardId;
    private int status; // 0: success, 1: fail

    public static BoardImageResponseDTO success(Long boardId) {
    	BoardImageResponseDTO dto = new BoardImageResponseDTO();
        dto.boardId = boardId;
        dto.status = 1;
        return dto;
    }

    public static BoardImageResponseDTO fail() {
    	BoardImageResponseDTO dto = new BoardImageResponseDTO();
        dto.status = 0;
        return dto;
    }

}
