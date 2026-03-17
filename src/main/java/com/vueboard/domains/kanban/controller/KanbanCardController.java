package com.vueboard.domains.kanban.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vueboard.domains.kanban.dto.CreateCardDTO;
import com.vueboard.domains.kanban.dto.CreatedKanbanCardDTO;
import com.vueboard.domains.kanban.dto.KanbanColumnDTO;
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
	public ResponseEntity<List<KanbanColumnDTO>> getKanbanCardByQuery(@RequestParam(required = false) String boardId) {
		if (boardId == null || boardId.isBlank()) {
			return ResponseEntity.badRequest().build();
		}
		List<KanbanColumnDTO> kanbanCards = kanbanCardService.getKanbanCardsByBoardId(boardId);
		return ResponseEntity.ok(kanbanCards);
	}

	// 칸반보드 Card 생성
	// 예: /kanban/card/create?boardId=...
	@PostMapping("/create")
	public ResponseEntity<CreatedKanbanCardDTO> createKanbanCard(
			@RequestParam(required = false) String boardId,
			@RequestBody CreateCardDTO request) {
		System.out.println("dto값: " + request);
		try {
			CreatedKanbanCardDTO created = kanbanCardService.createKanbanCard(boardId, request);
			return ResponseEntity.status(HttpStatus.CREATED).body(created);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// 칸반보드 Card 수정
//	@PostMapping("/update")
//	public ResponseEntity

}