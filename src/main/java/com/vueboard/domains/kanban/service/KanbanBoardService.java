package com.vueboard.domains.kanban.service;

import org.springframework.stereotype.Service;

import com.vueboard.domains.kanban.mapper.KanbanBoardMapper;
import com.vueboard.global.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KanbanBoardService {

	private final KanbanBoardMapper kanbanBoardMapper;
	private final JwtUtil jwtUtil;

	/**
	 * accessToken으로 pn을 추출한 뒤, pn에 해당하는 KanbanBoard의 boardId를 조회한다.
	 * 
	 * @return boardId (없으면 null)
	 */
	public String getBoardIdByAccessToken(String accessToken) {
		if (accessToken == null || accessToken.isBlank()) {
			return null;
		}
		long pn = Integer.parseInt(jwtUtil.extractPn(accessToken));
		System.out.println("토큰으로 가져온 pn값: "+ pn);
		return kanbanBoardMapper.getBoardIdByPn(pn);
	}

	
}