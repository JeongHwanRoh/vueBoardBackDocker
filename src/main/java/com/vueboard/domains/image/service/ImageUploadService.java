package com.vueboard.domains.image.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vueboard.domains.image.entity.UploadedTempImage;
import com.vueboard.domains.image.mapper.ImageUploadMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

	public final ImageUploadMapper uploadMapper;
	
	// 이미지 업로드 시 임시 저장 (게시글 최종 저장과 무관)
    @Value("${file.upload.path}")
    private String uploadPath;
	public String uploadImage(MultipartFile file) {
		System.out.println("요청된 파일: "+file);
		// 1. 파일명 생성
		String originalName = file.getOriginalFilename();
		String ext = originalName.substring(originalName.lastIndexOf("."));
		String storedName = UUID.randomUUID() + ext;

		// 2. 저장 경로(서버 저장 URL)
		String saveDir = uploadPath + "/temp";
		File dir = new File(saveDir);
		if (!dir.exists())
			dir.mkdirs();

		File saveFile = new File(saveDir, storedName);

		try {
			file.transferTo(saveFile);
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패", e);
		}
		
		// 3. 접근 URL
		String imageUrl="/uploads/temp/" + storedName;
		
		// 4. 엔터티에 데이터 추가 및 db 저장
		UploadedTempImage image=new UploadedTempImage();
		image.setTempImgId(uploadMapper.nextTempImageId());
		image.setOriginalName(originalName);
		image.setStoredName(storedName);
		image.setImageUrl(imageUrl );
		image.setFileSize(file.getSize());
		
		uploadMapper.insert(image);
		return imageUrl;
		

	}

}
