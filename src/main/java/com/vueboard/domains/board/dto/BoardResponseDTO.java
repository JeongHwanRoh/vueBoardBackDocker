package com.vueboard.domains.board.dto;

import java.time.LocalDateTime;

import com.vueboard.domains.board.entity.Board;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardResponseDTO {

	private long boardId;
	private String title;
	private String content;
	private Long pn;
	private String writer;
	private LocalDateTime regdate;

	public static BoardResponseDTO from(Board board) {
		return BoardResponseDTO.builder()
				.boardId(board.getBoardId())
				.title(board.getTitle())
				.content(board.getContent())
				.pn(board.getPn())
				.writer(board.getWriter())
				.regdate(board.getRegdate())
				.build();
	}

}
