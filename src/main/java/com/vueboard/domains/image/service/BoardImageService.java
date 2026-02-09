package com.vueboard.domains.image.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vueboard.domains.image.entity.SavedBoardImage;
import com.vueboard.domains.image.entity.UploadedTempImage;
import com.vueboard.domains.image.mapper.ImageSaveMapper;
import com.vueboard.domains.image.mapper.ImageUploadMapper;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BoardImageService {

	public final ImageUploadMapper uploadMapper;
	public final ImageSaveMapper saveMapper;

    @Value("${file.upload.path}")
    private String uploadPath;
	public void saveImages(Long boardId, String content) {
		// 1. content에서 imageUrl 추출
		List<String> imageUrls = extractImageUrls(content);
		if (imageUrls.isEmpty()) {
			return;
		}
		
		// 2. 임시 저장된 이미지 조회
		List<UploadedTempImage> tempImages=uploadMapper.findByImageUrls(imageUrls);
		
		for(UploadedTempImage temp:tempImages) {
			// 3. 파일 이동
			moveFile(boardId, temp.getStoredName());
			
			//4. TB_BOARD_IMAGE에 데이터 저장
			SavedBoardImage image=new SavedBoardImage();
			
			image.setImageId(saveMapper.nextImageId());
			image.setBoardId(boardId);
			image.setOriginalName(temp.getOriginalName());
			image.setStoredName(temp.getStoredName());
            image.setImageUrl("/uploads/board/" + boardId + "/" + temp.getStoredName());
            image.setFileSize(temp.getFileSize());          
            
            saveMapper.insert(image);
            
			
		}
	}
	
	// uploads/temp 파일을 uploads/board/{boardId}로 옮겨 이미지파일 게시글에 귀속시키기
	private void moveFile(Long boardId, String storedName) {
        Path source = Paths.get(uploadPath, "temp", storedName);
        Path targetDir = Paths.get(uploadPath, "board", String.valueOf(boardId));
        Path target = targetDir.resolve(storedName);

        try {
            Files.createDirectories(targetDir);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일 이동 실패", e);
        }
    }
	
	
	
	// HTML에서 img src 추출
	private List<String> extractImageUrls(String content) {
		List<String> urls = new ArrayList<>();

		Pattern pattern = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']");
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String url = matcher.group(1);
			if (url.startsWith("/uploads/temp/")) {
				urls.add(url);
			}
		}
		return urls;
	}

}
