package com.vueboard.domains.kanban.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// accessToken으로 pn추출-> boardId 생성 -> boardId 기반 column 3개 생성 -> boardId
	// 조회(accessToken->pn->boardId)
	@Transactional
	public String getBoardIdByAccessToken(String accessToken) {
		if (accessToken == null || accessToken.isBlank()) {
			return null;
		}

		// accessToken에서 pn 추출
		long pn = Integer.parseInt(jwtUtil.extractPn(accessToken));
		System.out.println("토큰으로 가져온 pn값: " + pn);
		// pn으로 boardId 조회
		String result = kanbanBoardMapper.getBoardIdByPn(pn);

		// 최초 로그인 시, TB_KANBAN_BOARD에 해당 pn의 데이터가 없을 수 있다.
		// 이 경우 TB_KANBAN_BOARD 에 해당 pn의 boardId 데이터를 생성한 뒤, boardId를 조회한다.
		if (result == null || result.isBlank()) {
			String boardId = UlidUtil.newUlid(); // ulid로 boardId 생성
			String boardName = "Board_" + pn; // boardName은 "Board_" + pn 으로 생성

			KanbanBoard kanbanBoard = new KanbanBoard();
			kanbanBoard.setBoardId(boardId);
			kanbanBoard.setPn(pn);
			kanbanBoard.setBoardName(boardName);

			kanbanBoardMapper.insertKanbanBoard(kanbanBoard); // TB_KANBAN_BOARD에 boardId 생성
			result = boardId;
			System.out.println("최초 로그인으로 TB_KANBAN_BOARD에 데이터가 없어서 새로 생성한 뒤 조회한 boardId: " + result);

			// boardId 생성 후 column 3개 생성
			kanbanBoardMapper.insertDefaultColumns(result, "TODO", 0);
			kanbanBoardMapper.insertDefaultColumns(result, "IN_PROGRESS", 1);
			kanbanBoardMapper.insertDefaultColumns(result, "DONE", 2);

		}

		return result;
	}


}