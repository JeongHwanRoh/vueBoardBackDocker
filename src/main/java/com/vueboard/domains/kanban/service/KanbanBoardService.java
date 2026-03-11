package com.vueboard.domains.kanban.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vueboard.domains.kanban.entity.KanbanBoard;
import com.vueboard.domains.kanban.entity.KanbanColumns;
import com.vueboard.domains.kanban.mapper.KanbanBoardMapper;
import com.vueboard.global.utils.JwtUtil;
import com.vueboard.global.utils.UlidUtil;

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
		System.out.println("토큰으로 가져온 pn값: " + pn);
		String result = kanbanBoardMapper.getBoardIdByPn(pn);

		// 최초 로그인 시, TB_KANBAN_BOARD에 해당 pn의 데이터가 없을 수 있다.
		// 이 경우 TB_KANBAN_BOARD 에 해당 pn의 데이터를 생성한 뒤, boardId를 조회한다.
		if (result == null || result.isBlank()) {
			String boardId = UlidUtil.newUlid(); //ulid로	boardId 생성
			String boardName = "Board_" + pn; // boardName은 "Board_" + pn 으로 생성

			KanbanBoard kanbanBoard = new KanbanBoard();
			kanbanBoard.setBoardId(boardId);
			kanbanBoard.setPn(pn);
			kanbanBoard.setBoardName(boardName);

			kanbanBoardMapper.insertKanbanBoard(kanbanBoard);
			result = boardId;
			System.out.println("최초 로그인으로 TB_KANBAN_BOARD에 데이터가 없어서 새로 생성한 뒤 조회한 boardId: " + result);
		}

		return result;
	}

	public List<KanbanColumns> getColumnByBoardId(String boardId) {
		
		return kanbanBoardMapper.findColumnByBoardId(boardId);
		
	}

}