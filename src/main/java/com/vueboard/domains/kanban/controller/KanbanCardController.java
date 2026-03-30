package com.vueboard.domains.kanban.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vueboard.domains.kanban.dto.CreateCardRequestDTO;
import com.vueboard.domains.kanban.dto.CreateCardResponseDTO;
import com.vueboard.domains.kanban.dto.CreateCardScheduleRequestDTO;
import com.vueboard.domains.kanban.dto.KanbanColumnDTO;
import com.vueboard.domains.kanban.dto.ReorderCardDTO;
import com.vueboard.domains.kanban.dto.UpdateKanbanCardDTO;
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
	public ResponseEntity<CreateCardResponseDTO> createKanbanCard(
			@RequestParam(required = false) String boardId,
			@RequestBody CreateCardRequestDTO request) {
		System.out.println("dto: " + request);
		try {
			CreateCardResponseDTO created = kanbanCardService.createKanbanCard(boardId, request);
			return ResponseEntity.status(HttpStatus.CREATED).body(created);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// 칸반보드 Card 일정 추가
	@PatchMapping("/schedule")
	public ResponseEntity<Void> addScheduleToKanbanCard(
			@RequestBody CreateCardScheduleRequestDTO request) {
		System.out.println("일정 추가 요청: " + request);
		try {
			kanbanCardService.addScheduleToKanbanCard(request);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			System.out.println("일정 추가 실패 원인: " + e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
	
	// 칸반보드 Card 수정
	// PatchMapping 사용 (부분 업데이트, Spring Security의 CORS Preflight 요청 허용)
	@PatchMapping("/update")
	public ResponseEntity<Void> updateKanbanCard(
			@RequestBody UpdateKanbanCardDTO request,
			@RequestParam(required = false) String boardId) {
		System.out.println("dto 요청: " + request);
		try {
			kanbanCardService.updateKanbanCard(boardId, request);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			System.out.println("update 실패 원인: " + e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
	
	// 칸반보드 Card 드래그앤드롭
	@PatchMapping("/reorder")
	public ResponseEntity<Void> reorderKanbanCard(
			@RequestParam(required = false) String boardId,
			@RequestBody List<ReorderCardDTO> cards) {
		System.out.println("드래그앤드롭 요청: " + cards);
		try {
			kanbanCardService.reorderKanbanCard(boardId, cards);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			System.out.println("reorder 실패 원인: " + e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
	
	// 칸반보드 Card 삭제
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteKanbanCard(
			@RequestParam(required=false) long cardId) {
		
		kanbanCardService.deleteKanbanCard(cardId);
		return ResponseEntity.ok().build();
	}
	
	
	

}