package com.vueboard.domains.kanban.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.domains.kanban.entity.KanbanBoard;
import com.vueboard.domains.kanban.entity.KanbanColumns;

@Mapper
public interface KanbanBoardMapper {

	String getBoardIdByPn(@Param("pn") long pn);

	int insertKanbanBoard(KanbanBoard kanbanBoard);

	int insertDefaultColumns(String boardId, String columnName, long orderNum);

}