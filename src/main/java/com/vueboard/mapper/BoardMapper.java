package com.vueboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.entity.Board;

@Mapper
public interface BoardMapper {
//    List<Board> getAllBoards();
	List<Board> selectBoardsByPage(@Param("offset") int offset, @Param("size") int size);  //페이징 처리된 게시물 조회(10개씩)
    int selectTotalCount();  // 전체 게시물 개수
	Board getBoardById(Long boardId); // 특정 게시글 조회
//    int insertBoard(Board board); // 게시글 등록
    int updateBoard(Board board);  // 게시글 수정
    int deleteBoard(Long boardId); // 게시글 삭제
}

