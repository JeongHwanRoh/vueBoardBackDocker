package com.vueboard.domains.image.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vueboard.domains.board.mapper.BoardMapper;
import com.vueboard.domains.board.service.BoardService;
import com.vueboard.domains.image.service.ImageUploadService;
import com.vueboard.global.utils.CookieUtil;
import com.vueboard.global.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/image")
public class ImageUploadController {
	
	private final ImageUploadService uploadService;
    @PostMapping("/upload")
    public Map<String, String> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {
        String imageUrl = uploadService.uploadImage(file);
        return Map.of("imageUrl", imageUrl);
    }
	

}
