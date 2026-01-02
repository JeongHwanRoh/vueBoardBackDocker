package com.vueboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vueboard.entity.Board;
import com.vueboard.entity.User;
import com.vueboard.mapper.BoardMapper;
import com.vueboard.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardMapper boardMapper;

	// 페이징 처리된 게시물 조회(10개씩)
	public List<Board> getBoardList(int offset, int size) {
		
		return boardMapper.selectBoardsByPage(offset,size);
		
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
//	public int insertBoard(Board board) {
//
//		return boardMapper.insertBoard(board);
//	}

	// 게시글 삭제
	public int deleteBoard(long boardId) {

		return boardMapper.deleteBoard(boardId);

	}

}
