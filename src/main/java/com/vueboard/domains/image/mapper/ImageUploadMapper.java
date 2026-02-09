package com.vueboard.domains.image.mapper;

import java.util.List;

import com.vueboard.domains.image.entity.UploadedTempImage;

public interface ImageUploadMapper {

	Long nextTempImageId(); //업로드 시 imageId 자동배정
	void insert(UploadedTempImage image); // 업로드 이미지 저장
	List<UploadedTempImage> findByImageUrls(List<String> imageUrls); // imageUrl 기준 업로드된 이미지 조회(TEMP_BOARD_IMAGE 조회)

}
