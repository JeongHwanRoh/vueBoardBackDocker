package com.vueboard.domains.kanban.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface KanbanBoardMapper {

	String getBoardIdByPn(@Param("pn") long pn);

}