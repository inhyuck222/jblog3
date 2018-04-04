package com.cafe24.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	
	@Autowired
	CategoryDao categoryDao;
		
	public List<CategoryVo> getCtegories(Long blogNo){		
		List<CategoryVo> list = categoryDao.selectList(blogNo);
		
		return list;
	}
	
	public boolean addNewCategory(Long blogNo, String name, String description) {
		CategoryVo category = new CategoryVo();
		category.setBlogNo(blogNo);
		category.setName(name);
		category.setDescription(description);		
		boolean result = categoryDao.insertNewCategory(category);
		
		return result;
	}
	
	public boolean deleteCategory(Long categoryNo) {
		boolean result = categoryDao.delete(categoryNo);
		
		return result;
	}

}
