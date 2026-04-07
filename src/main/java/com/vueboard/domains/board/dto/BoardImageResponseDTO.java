package com.vueboard.domains.board.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardImageResponseDTO {
	private Long boardId;
    private int status; // 0: fail, 1: success

    public static BoardImageResponseDTO success(Long boardId) {
    	return BoardImageResponseDTO.builder()
    			.boardId(boardId)
    			.status(1)
    			.build();
    }

    public static BoardImageResponseDTO fail() {
    	return BoardImageResponseDTO.builder()
    			.status(0)
    			.build();
    }

}
