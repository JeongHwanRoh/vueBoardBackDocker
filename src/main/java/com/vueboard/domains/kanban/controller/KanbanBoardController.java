package com.vueboard.domains.kanban.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vueboard.domains.kanban.entity.KanbanColumns;
import com.vueboard.domains.kanban.service.KanbanBoardService;
import com.vueboard.global.utils.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kanban/board")
public class KanbanBoardController {

	private final KanbanBoardService kanbanBoardService;
	private final CookieUtil cookieUtil;

	/**
	 * 현재 로그인 사용자의 TB_KANBAN_BOARD.boardId 조회
	 * 
	 * 응답 예: {"boardId":"01KANBANDEFAULT..."}
	 */
	@GetMapping("/boardId")
	public ResponseEntity<Map<String, Object>> getBoardId(HttpServletRequest request) {
		String accessToken = cookieUtil.resolveAccessTokenFromCookie(request);
		String boardId = kanbanBoardService.getBoardIdByAccessToken(accessToken);

		if (boardId == null || boardId.isBlank()) {
			return ResponseEntity.notFound().build();
		}

		Map<String, Object> result = new HashMap<>();
		result.put("boardId", boardId);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/column/{boardId}")
	public ResponseEntity<?> getColumnByBoardId(@PathVariable String boardId) {
		List<KanbanColumns> result = kanbanBoardService.getColumnByBoardId(boardId);
		return ResponseEntity.ok(result);	
		
	}
	
	
}