package com.vueboard.domains.kanban.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vueboard.domains.kanban.dto.CreateCardDTO;
import com.vueboard.domains.kanban.dto.KanbanColumnDTO;
import com.vueboard.domains.kanban.entity.KanbanCard;
import com.vueboard.domains.kanban.mapper.KanbanCardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KanbanCardService {

	private final KanbanCardMapper kanbanCardMapper;

	public List<KanbanColumnDTO> getKanbanCardsByBoardId(String boardId) {
		return kanbanCardMapper.findByBoardId(boardId);
	}

//	@Transactional
//	public KanbanCard createKanbanCard(CreateCardDTO request) {
//
//
//		int inserted = kanbanCardMapper.insertKanbanCard(request);
//		
//		
//		if (inserted != 1) {
//			throw new IllegalArgumentException("failed to insert kanban card");
//		}
//		return kanbanCardMapper.findByCardId(request.getCardId());
//		
////		KanbanCard created = kanbanCardMapper.findByCardId(card.getCardId());
////		return created != null ? created : card;
//	}

}