package com.vueboard.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
   
   private long boardId;
   private String title;
   private String content;
   private Long pn;
   private LocalDateTime  regdate;
   private int viewcnt;
   private String category;
   
}
