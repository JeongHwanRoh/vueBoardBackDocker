package com.vueboard.domains.kanban.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vueboard.domains.kanban.dto.CreateCardDTO;
import com.vueboard.domains.kanban.dto.CreatedKanbanCardDTO;
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

	/**
	 * 카드 생성 로직
	 * 1) columnId 조회 (boardId + columnName)
	 * 2) orderNum 계산 (없으면 0부터)
	 * 3) cardId 시퀀스 조회 후 TB_KANBAN_CARD insert
	 * 4) TB_KANBAN_CARD_INFO insert
	 */
	@Transactional
	public CreatedKanbanCardDTO createKanbanCard(String boardId, CreateCardDTO request) {

		// 1) columnId 조회
		String columnId = kanbanCardMapper.findColumnId(boardId, request.getColumnName());
		// 2) orderNum 계산
		long orderNum = kanbanCardMapper.nextOrderNum(columnId);

		// 3) 시퀀스에서 cardId 가져와 TB_KANBAN_CARD에 insert
		long cardId = kanbanCardMapper.nextCardId();

		KanbanCard card = new KanbanCard();
		card.setCardId(cardId);
		card.setColumnId(columnId);
		card.setTitle(request.getTitle());
		card.setOrderNum(orderNum);

		int insertedCard = kanbanCardMapper.insertKanbanCard(card);
		if (insertedCard != 1) {
			throw new IllegalArgumentException("failed to insert TB_KANBAN_CARD");
		}

		// 4)  TB_KANBAN_CARD_INFO insert
		int insertedInfo = kanbanCardMapper.insertKanbanCardInfo(cardId, request.getCardInfo());
		if (insertedInfo != 1) {
			throw new IllegalArgumentException("failed to insert TB_KANBAN_CARD_INFO");
		}

		return new CreatedKanbanCardDTO(cardId, columnId, request.getTitle(), orderNum, request.getCardInfo());
	}
}