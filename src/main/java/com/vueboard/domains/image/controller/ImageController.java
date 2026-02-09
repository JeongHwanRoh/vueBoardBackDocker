package com.vueboard.domains.image.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vueboard.domains.board.mapper.BoardMapper;
import com.vueboard.domains.board.service.BoardService;
import com.vueboard.domains.image.service.ImageUploadService;
import com.vueboard.domains.image.service.BoardImageService;
import com.vueboard.global.utils.CookieUtil;
import com.vueboard.global.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/image")
public class ImageController {
	
	private final ImageUploadService imageUploadService;
	private final BoardImageService boardImageService;

	@PostMapping("/upload")
    public Map<String, String> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {
        String imageUrl = imageUploadService.uploadImage(file);
        return Map.of("imageUrl", imageUrl);
    }

	@PostMapping("/save/{targetId}")
    public void saveBoardImages(@PathVariable Long boardId,@PathVariable String content) {
		boardImageService.saveImages(boardId, content);
	}
    
	

}
