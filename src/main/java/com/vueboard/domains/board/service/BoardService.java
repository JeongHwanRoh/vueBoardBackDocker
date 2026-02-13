package com.vueboard.domains.board.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.mapper.AuthMapper;
import com.vueboard.domains.board.dto.BoardResponseDTO;
import com.vueboard.domains.board.entity.Board;
import com.vueboard.domains.board.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardMapper boardMapper;

	// 페이징 처리된 게시물 조회(10개씩)
	public List<Board> getBoardList(int offset, int size) {

		return boardMapper.selectBoardsByPage(offset, size);

	}
	
	// 게시글 번호 기준 최신 5개 게시물 조회 
	public List<Board> getRecentFiveBoardList() {

		return boardMapper.selectRecentFiveBoards();

	}

	// 전체 게시물 수 구하기
	public int getTotalCount() {

		return boardMapper.selectTotalCount();
	}
//	public List<Board> getAllBoards() {
//
//		return boardMapper.getAllBoards();
//	}

	// 특정 게시글 조회
	public Board getBoardById(long boardId) {

		return boardMapper.getBoardById(boardId);
	}

	// 게시글 등록
	public int insertBoard(Board board) {

		return boardMapper.insertBoard(board);
	}

	// 게시글 수정
	public BoardResponseDTO updateBoard(Board board, long pn) {
		// 1.기존 글 가져오기
		Board existing = boardMapper.getBoardById(board.getBoardId());
		// 2. 본인만 수정 가능하게 작성자 검증(pn을 기준으로)
		if (!existing.getPn().equals(pn)) {
			throw new SecurityException("본인이 작성한 게시글만 수정 가능합니다.");

		}
		// 3. 수정 후 Board 엔터티에 반영
		int updated = boardMapper.updateBoard(board);
		System.out.println("수정결과: "+updated);
		if (updated == 0) {
			throw new RuntimeException("게시글 수정에 실패했습니다.");
		}
		// 4. 수정된 데이터 다시 조회 (정합성 보장)
		Board updatedBoard = boardMapper.getBoardById(board.getBoardId());
		// 5. 엔터티에서 변경된 데이터를 ResponseDTO에도 반영
		return new BoardResponseDTO(updatedBoard);

	}

	// 게시글 삭제
	public String deleteById(long boardId, long pn) {
		// 1.기존 글 가져오기
		 Board existing=boardMapper.getBoardById(boardId);
		 
		// 2. 본인만 삭제 가능하게 작성자 검증
		if(!existing.getPn().equals(pn)) {
			throw new SecurityException("본인이 작성한 게시글만 삭제 가능합니다.");
		}
		boardMapper.deleteById(boardId);
		return "게시글이 성공적으로 삭제되었습니다.";

	}
	// TB_BOARD.CONTENT 내 이미지 URL 경로도 TB_BOARD_IMAGE의 IMAGE_URL로 변경 처리 (SQL 쿼리로 처리)
	public BoardResponseDTO updateBoardContent(Long boardId) {
		boardMapper.updateBoardContent(boardId);
		Board updatedBoard = boardMapper.getBoardById(boardId);
		return new BoardResponseDTO(updatedBoard);
		
	}

}
