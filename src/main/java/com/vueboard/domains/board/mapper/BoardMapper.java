package com.vueboard.domains.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;

import com.vueboard.domains.board.dto.BoardResponseDTO;
import com.vueboard.domains.board.entity.Board;

@Mapper
public interface BoardMapper {
//    List<Board> getAllBoards();
	List<Board> selectBoardsByPage(@Param("offset") int offset, @Param("size") int size);  //페이징 처리된 게시물 조회(10개씩)
	List<Board> selectRecentFiveBoards(); // 게시글 번호 기준 최신 5개 게시물 조회 
    int selectTotalCount();  // 전체 게시물 개수
	Board getBoardById(Long boardId); // 특정 게시글 조회
   int insertBoard(Board board); // 게시글 등록
    int updateBoard(Board board);  // 게시글 수정
    int deleteBoard(Long boardId); // 게시글 삭제
    int updateBoardContent(Long boardId); // TB_BOARD.CONTENT 내 이미지 URL 경로도 TB_BOARD_IMAGE의 IMAGE_URL로 변경 처리 (SQL 쿼리로 처리)
}

