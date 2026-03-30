package com.vueboard.domains.kanban.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vueboard.domains.kanban.dto.CreateCardRequestDTO;
import com.vueboard.domains.kanban.dto.CreateCardResponseDTO;
import com.vueboard.domains.kanban.dto.CreateCardScheduleRequestDTO;
import com.vueboard.domains.kanban.dto.KanbanColumnDTO;
import com.vueboard.domains.kanban.dto.ReorderCardDTO;
import com.vueboard.domains.kanban.dto.UpdateCardScheduleRequestDTO;
import com.vueboard.domains.kanban.dto.UpdateKanbanCardDTO;
import com.vueboard.domains.kanban.entity.KanbanCard;
import com.vueboard.domains.kanban.entity.KanbanCardInfo;
import com.vueboard.domains.kanban.mapper.KanbanCardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KanbanCardService {

	private final KanbanCardMapper kanbanCardMapper;

	public List<KanbanColumnDTO> getKanbanCardsByBoardId(String boardId) {
		// TB_KANBAN_CARD, TB_KANBAN_CARD_INFO, TB_KANBAN_COLUMN, TB_KANBAN_CARD_SCHEDULE을 조인해서 조회
		return kanbanCardMapper.findByBoardId(boardId);
	}


	 // 카드 생성 로직 1) columnId 조회 (boardId + columnName) 2) orderNum 계산 (없으면 0부터) 3)
	 // cardId 시퀀스 조회 후 TB_KANBAN_CARD insert 4) TB_KANBAN_CARD_INFO insert

	@Transactional
	public CreateCardResponseDTO createKanbanCard(String boardId, CreateCardRequestDTO request) {

		// 1) columnId 조회
		long columnId = kanbanCardMapper.findColumnId(boardId, request.getColumnName());
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

		// 4) TB_KANBAN_CARD_INFO insert
		int insertedInfo = kanbanCardMapper.insertKanbanCardInfo(cardId, request.getCardInfo());
		if (insertedInfo != 1) {
			throw new IllegalArgumentException("failed to insert TB_KANBAN_CARD_INFO");
		}

		return new CreateCardResponseDTO(cardId, columnId, request.getTitle(), orderNum, request.getCardInfo());
	}
	
	// 카드 일정 추가 로직
	public void addScheduleToKanbanCard(CreateCardScheduleRequestDTO request) {
	

		int updated = kanbanCardMapper.insertKanbanCardSchedule(request);
		if (updated != 1) {
			throw new IllegalArgumentException("카드 일정 추가 실패");
		}
		
	}
	
	
	// 카드 수정 로직 
	//	1) columnId 조회 (boardId + columnName) 
	//	2) orderNum 계산 (동일한 column 내에서 orderNum이 변경될 수 있기 때문에, 현재 카드의 orderNum과 요청받은 orderNum이 다르면 orderNum 재계산)
	//	3) TB_KANBAN_CARD 업데이트
	//	4) TB_KANBAN_CARD_INFO 업데이트
	@Transactional
	public void updateKanbanCard(String boardId, UpdateKanbanCardDTO request) {
		System.out.println("보드 ID: " + boardId);
		System.out.println("서비스 요청: " + request);

		// 1) columnId 조회
		long columnId = kanbanCardMapper.findColumnId(boardId, request.getColumnName());
		System.out.println("조회된 columnId: " + columnId);
		// 2) orderNum 계산 (동일한 column 내에서 orderNum이 변경될 수 있기 때문에, 현재 카드의 orderNum과 요청받은
		// orderNum이 다르면 orderNum 재계산)
		long orderNum = kanbanCardMapper.nextOrderNum(columnId);
		System.out.println("계산된 orderNum: " + orderNum);

		KanbanCard card = new KanbanCard();
		card.setCardId(request.getCardId());
		card.setColumnId(columnId);
		card.setTitle(request.getTitle());
		card.setOrderNum(orderNum);

		System.out.println("업데이트할 카드 정보: " + card);
		// 3) TB_KANBAN_CARD 업데이트
		int cardUpdated = kanbanCardMapper.updateKanbanCard(card);
		if (cardUpdated != 1) {
			throw new IllegalArgumentException("TB_KANBAN_CARD 업데이트 실패");
		}
		// 4) TB_KANBAN_CARD_INFO 업데이트
		KanbanCardInfo cardInfo = new KanbanCardInfo();
		cardInfo.setCardId(request.getCardId());
		cardInfo.setCardInfo(request.getCardInfo());

		int infoUpdated = kanbanCardMapper.updateKanbanCardInfo(cardInfo);
		if (infoUpdated != 1) {
			throw new IllegalArgumentException("TB_KANBAN_CARD_INFO 업데이트 실패");
		}
	}
	

	// 카드 일정 수정 로직
	public void updateKanbanCardSchedule(UpdateCardScheduleRequestDTO request) {
		int updated = kanbanCardMapper.updateKanbanCardSchedule(request);
		if (updated != 1) {
			throw new IllegalArgumentException("카드 일정 수정 실패");
		}
		
	}

	// 카드 드래그앤드롭 로직
	@Transactional
	public void reorderKanbanCard(String boardId, List<ReorderCardDTO> cards) {
		for (ReorderCardDTO card : cards) {
			// 1) columnName → columnId 조회
			long columnId = kanbanCardMapper.findColumnId(boardId, card.getColumnName());
			System.out.println("조회된 columnId for reorder: " + columnId);
			// 2) TB_KANBAN_CARD 업데이트 (cardId, columnId, orderNum)
			KanbanCard kanbanCard = new KanbanCard();
			kanbanCard.setCardId(card.getCardId());
			kanbanCard.setColumnId(columnId);
			kanbanCard.setOrderNum(card.getOrderNum());
			System.out.println("업데이트할 카드 정보 for reorder: " + kanbanCard);
			kanbanCardMapper.reorderKanbanCard(kanbanCard);
		}
	}

	// 카드 삭제 로직
	public int deleteKanbanCard(long cardId) {
		// 카드 삭제 시, TB_KANBAN_CARD_INFO도 같이 삭제되어야 함 (FK 제약조건)
		// TB_KANBAN_CARD_INFO 먼저 삭제 -> TB_KANBAN_CARD 삭제
		int deletedInfo = kanbanCardMapper.deleteKanbanCardInfo(cardId);
		int deletedCard = kanbanCardMapper.deleteKanbanCard(cardId);

		if (deletedCard != 1) {
			throw new IllegalArgumentException("TB_KANBAN_CARD 삭제 실패");
		}

		return deletedCard;

	}

	


}