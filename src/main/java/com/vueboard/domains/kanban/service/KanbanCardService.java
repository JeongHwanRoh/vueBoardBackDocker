package com.vueboard.domains.kanban.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vueboard.domains.kanban.entity.KanbanCard;
import com.vueboard.domains.kanban.mapper.KanbanCardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KanbanCardService {

	private final KanbanCardMapper kanbanCardMapper;

	public List<KanbanCard> getKanbanCardsByBoardId(String boardId) {
		return kanbanCardMapper.findByBoardId(boardId);
	}

}