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
	
//	int insertKanbanCard(CreateCardDTO card);
//	
//	KanbanCard findByCardId(@Param("cardId") long cardId);

}