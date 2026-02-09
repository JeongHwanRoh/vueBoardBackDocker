package com.vueboard.domains.image.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UploadedTempImage {
	
	private Long tempImgId;
	private String originalName;
	private String storedName;
	private String imageUrl;
	private Long fileSize;
	private LocalDateTime createdAt;
	

}
