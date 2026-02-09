package com.vueboard.domains.image.mapper;

import com.vueboard.domains.image.entity.UploadedTempImage;

public interface ImageUploadMapper {

	Long nextTempImageId(); //업로드 시 imageId 자동배정
	void insert(UploadedTempImage image); // 업로드 이미지 저장

}
