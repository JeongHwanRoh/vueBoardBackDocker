package com.vueboard.domains.kanban.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.domains.kanban.dto.CreateCardDTO;
import com.vueboard.domains.kanban.dto.KanbanColumnDTO;
import com.vueboard.domains.kanban.entity.KanbanCard;

@Mapper
public interface KanbanCardMapper {

	List<KanbanColumnDTO> findByBoardId(@Param("boardId") String boardId);
	
	long findColumnId(@Param("boardId") String boardId, @Param("columnName") String columnName);

	long nextOrderNum(@Param("columnId") long columnId);

	long nextCardId();

	int insertKanbanCard(KanbanCard card);

	int insertKanbanCardInfo(@Param("cardId") long cardId, @Param("cardInfo") String cardInfo);
}