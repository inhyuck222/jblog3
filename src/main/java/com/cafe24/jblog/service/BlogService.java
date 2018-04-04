package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.UserVo;

@Service
public class BlogService {

	@Autowired
	BlogDao blogDao;
	
	@Autowired
	CategoryDao categoryDao;

	public boolean makeNewBLog(UserVo user) {
		boolean result = blogDao.insertBlog(user);
		if(result == false) {
			return false;
		}
				
		result = makeDefaultCategory(user.getId());
		
		return result;
	}
	
	public boolean makeDefaultCategory(String userId) {
		BlogVo blog = getBlog(userId);
		if(blog == null) {
			return false;
		}
		
		boolean result = categoryDao.insertDefaultCategory(blog.getNo());
		
		return result;
	}

	public BlogVo getBlog(String id) {
		BlogVo blog = blogDao.selectBlog(id);

		return blog;
	}
	
	public boolean updateBlogInfo(BlogVo blog) {
		int count = blogDao.updateBlogInfo(blog);
		
		return count == 1;
	}
	
	public void adminBasicUpdate(BlogVo blog, String title, MultipartFile multipartFile, FileUploadService fileUploadService) {
		
		if(title == null || "".equals(title)) {
			title = blog.getTitle();
		}
		
		String logoPath = fileUploadService.restore(multipartFile);
		blog.setTitle(title);
		if("".equals(logoPath)) {
			return;
		}		
		blog.setLogoPath(logoPath);
	}

}
