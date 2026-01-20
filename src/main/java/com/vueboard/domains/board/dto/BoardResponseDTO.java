package com.vueboard.domains.board.dto;

import java.time.LocalDateTime;

import com.vueboard.domains.board.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardResponseDTO {

	private long boardId;
	private String title;
	private String content;
	private Long pn;
	private String writer;
	private LocalDateTime regdate;
	private int viewcnt;
	private String category;

	public BoardResponseDTO(Board board) {
		this.boardId = board.getBoardId();
		this.title = board.getTitle();
		this.content = board.getContent();
		this.pn = board.getPn();
		this.writer = board.getWriter();
		this.regdate = board.getRegdate();
		this.viewcnt = board.getViewcnt();
		this.category = board.getCategory();
	}

}
