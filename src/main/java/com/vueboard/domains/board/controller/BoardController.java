package com.vueboard.domains.board.controller;

import com.vueboard.domains.auth.dto.UserResponseDTO;
import com.vueboard.domains.board.dto.BoardImageResponseDTO;
import com.vueboard.domains.board.dto.BoardResponseDTO;
import com.vueboard.domains.board.entity.Board;
import com.vueboard.domains.board.mapper.BoardMapper;
import com.vueboard.domains.board.service.BoardService;
import com.vueboard.global.utils.CookieUtil;
import com.vueboard.global.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // Vue 포트 허용(일반vue용)
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Vue 포트 허용(Nuxt vue용)
public class BoardController {

	private final BoardService boardService;
	private final BoardMapper boardMapper;
	private final CookieUtil cookieutil;
	private final JwtUtil jwtutil;

	// 전체 게시글 목록
//	@GetMapping("/list")
//	public List<Board> getAllBoards() {
//		return boardService.getAllBoards();
//	}
	// 페이징 처리(10개씩)
	@GetMapping("/list")
	public Map<String, Object> getBoards(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {

		int offset = (page - 1) * size; // 각 페이지 시작점

		List<Board> boards = boardService.getBoardList(offset, size); // 해당 페이지의 게시물 목록
		int totalCount = boardService.getTotalCount(); // 전체 게시물 수

		Map<String, Object> result = new HashMap<>();
		result.put("boards", boards);
		result.put("totalCount", totalCount);

		return result;
	}

	// 게시글 번호 기준 최신 5개 게시물 조회
	@GetMapping("/listRecentFive")
	public Map<String, Object> getRecentFiveBoards() {
		List<Board> boards = boardService.getRecentFiveBoardList(); // 게시글번호 기준 최근 5개 불러오기
		Map<String, Object> result = new HashMap<>();
		result.put("boards", boards);
		return result;

	}

	// 특정 게시글 조회
	@GetMapping("/{boardId}")
	public Board getBoardById(@PathVariable Long boardId) {
		return boardService.getBoardById(boardId);
	}

	// 게시글 등록
	@PostMapping("/create")
	public ResponseEntity<BoardImageResponseDTO> createBoard(@RequestBody Board board, Authentication authentication) {
		UserResponseDTO user = (UserResponseDTO) authentication.getPrincipal();

		board.setPn(user.getPn());
		board.setWriter(user.getName());

		boardService.insertBoard(board);

		// 이제 여기서 boardId가 들어있음
		return ResponseEntity.ok(BoardImageResponseDTO.success(board.getBoardId()));
	}
	
	// TB_BOARD.CONTENT 내 이미지 URL 경로도 TB_BOARD_IMAGE의 IMAGE_URL로 변경 처리 (SQL 쿼리로 처리)
	@PutMapping("/content/update/{boardId}")
	public BoardResponseDTO updateBoardContent(@PathVariable Long boardId){
		return boardService.updateBoardContent(boardId);
	}
	
	
	// 게시글 수정
	@PutMapping("/update/{boardId}")
	public ResponseEntity<BoardResponseDTO> updateBoard(@PathVariable Long boardId, @RequestBody Board board,
			HttpServletRequest request) {
		// 1. 쿠키에서 액세스 토큰 추출
		String accessToken = cookieutil.resolveAccessTokenFromCookie(request);
		System.out.println("accessToken값 :" + accessToken);

		// 2. jwt에서 pn값 뽑아내기
		long pn = Integer.parseInt(jwtutil.extractPn(accessToken));
		System.out.println("pn값 :" + pn);

		// 3. PathVariable로 받은 boardId를 엔터티에 주입
		board.setBoardId(boardId);

		// 4. 서비스 호출
		BoardResponseDTO dto = boardService.updateBoard(board, pn);

		return ResponseEntity.ok(dto);
	}

	// 게시글 삭제
	@DeleteMapping("/delete/{boardId}")
	public String deleteBoard(@PathVariable Long boardId) {
		int result = boardService.deleteBoard(boardId);
		return result > 0 ? "success" : "fail";
	}

}
