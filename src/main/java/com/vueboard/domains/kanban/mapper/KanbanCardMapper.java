package com.vueboard.domains.kanban.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.domains.kanban.entity.KanbanCard;

@Mapper
public interface KanbanCardMapper {

	List<KanbanCard> findByBoardId(@Param("boardId") String boardId);

}