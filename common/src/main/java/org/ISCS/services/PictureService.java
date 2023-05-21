package org.ISCS.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PictureService {
	
	/**
	 * 
	 *
	 * @param  
	 * @return
	 */
	Map<String,Object> uploadPicture(MultipartFile uploadFile) throws Exception;
	
	boolean deleteFile(String picName) throws Exception;
}
