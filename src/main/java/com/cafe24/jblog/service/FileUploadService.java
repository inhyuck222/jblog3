package com.cafe24.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	private static final String SAVE_PATH = "/uploads";
	private static final String PREFIX_URL = "/uploads/images/";
	
	public String restore(MultipartFile multipartFile) {
		if("".equals(multipartFile.getOriginalFilename())) {
			return "";
		}
		
		String url = "";
		
		try {			
			String originFileName = multipartFile.getOriginalFilename();
			String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
			String saveFileName = getSaveFileName(extName);
			
			writeFile(multipartFile, saveFileName);

			url = PREFIX_URL + saveFileName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
				
		return url;
	}

	
	
	public void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		byte[] data = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
		fos.write(data);
		fos.close();
	}
	
	
	
	public String getSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();

		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += extName;

		return fileName;
	}

}
