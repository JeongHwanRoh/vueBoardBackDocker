package com.vueboard.domains.image.mapper;

import com.vueboard.domains.image.entity.SavedBoardImage;
import com.vueboard.domains.image.entity.UploadedTempImage;

public interface ImageSaveMapper {

	Long nextImageId();
	void insert(SavedBoardImage image);



}
