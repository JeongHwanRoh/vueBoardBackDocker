package com.vueboard.domains.kanban.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vueboard.domains.kanban.entity.KanbanCard;
import com.vueboard.domains.kanban.service.KanbanCardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kanban/card")
public class KanbanCardController {

	private final KanbanCardService kanbanCardService;

	// 칸반보드 Card 조회 (query param)
	// 예: /kanban/card/list?boardId=...
	@GetMapping("/list")
	public ResponseEntity<List<KanbanCard>> getKanbanCardByQuery(@RequestParam(required = false) String boardId) {
		if (boardId == null || boardId.isBlank()) {
			return ResponseEntity.badRequest().build();
		}
		List<KanbanCard> kanbanCards = kanbanCardService.getKanbanCardsByBoardId(boardId);
		return ResponseEntity.ok(kanbanCards);
	}

}