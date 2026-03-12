package com.vueboard.domains.kanban.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.vueboard.domains.kanban.dto.CreateCardDTO;
import com.vueboard.domains.kanban.dto.CreatedKanbanCardDTO;
import com.vueboard.domains.kanban.entity.KanbanCard;
import com.vueboard.domains.kanban.mapper.KanbanCardMapper;

class KanbanCardServiceTest {

	@Test
	void createKanbanCard_happyPath_assignsIdsAndOrder() {
		KanbanCardMapper mapper = mock(KanbanCardMapper.class);
		KanbanCardService service = new KanbanCardService(mapper);

		when(mapper.findColumnId("B1", "TODO")).thenReturn("C10");
		when(mapper.nextOrderNum("C10")).thenReturn(0L);
		when(mapper.nextCardId()).thenReturn(123L);
		when(mapper.insertKanbanCard(any(KanbanCard.class))).thenReturn(1);
		when(mapper.insertKanbanCardInfo(123L, "info")).thenReturn(1);

		CreateCardDTO dto = new CreateCardDTO();
		dto.setColumnName("TODO");
		dto.setTitle("t");
		dto.setCardInfo("info");

		CreatedKanbanCardDTO created = service.createKanbanCard("B1", dto);

		assertEquals(123L, created.getCardId());
		assertEquals("C10", created.getColumnId());
		assertEquals(0L, created.getOrderNum());
		assertEquals("t", created.getTitle());
		assertEquals("info", created.getCardInfo());

		verify(mapper).findColumnId("B1", "TODO");
		verify(mapper).nextOrderNum("C10");
		verify(mapper).nextCardId();
		verify(mapper).insertKanbanCard(any(KanbanCard.class));
		verify(mapper).insertKanbanCardInfo(123L, "info");
	}

	@Test
	void createKanbanCard_whenColumnNotFound_throws() {
		KanbanCardMapper mapper = mock(KanbanCardMapper.class);
		KanbanCardService service = new KanbanCardService(mapper);

		when(mapper.findColumnId("B1", "TODO")).thenReturn(null);

		CreateCardDTO dto = new CreateCardDTO();
		dto.setColumnName("TODO");
		dto.setTitle("t");

		assertThrows(IllegalArgumentException.class, () -> service.createKanbanCard("B1", dto));
		verify(mapper).findColumnId("B1", "TODO");
		verifyNoMoreInteractions(mapper);
	}
}