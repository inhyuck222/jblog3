package com.cafe24.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.dto.JSONResult;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.service.CategoryService;
import com.cafe24.jblog.service.PostService;
import com.cafe24.jblog.vo.CategoryVo;

@Controller("categoryAPIController")
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	CategoryDao categoryDao;
	
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public JSONResult addCategories(
			@RequestParam(value="blogNo", required=true) Long blogNo,
			@RequestParam(value="name", required=true) String name, 
			@RequestParam(value="description", required=true) String description 
			) {
		
		boolean result = categoryService.addNewCategory(blogNo, name, description);
		if(result == false) {
			return JSONResult.fail("fail to add new category");
		}
		
		List<CategoryVo> list = categoryDao.selectList(blogNo);
		
		return JSONResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public JSONResult deleteCategory(
			@RequestParam(value="no", required=true) Long categoryNo) {
		
		postService.deletByCategory(categoryNo);
		boolean result = categoryService.deleteCategory(categoryNo);
		
		if(result == false) {
			return JSONResult.fail("fail to delete the category");
		}
		
		
		return JSONResult.success("deleted");
	}
	
	@ResponseBody
	@RequestMapping(value="/getcategories", method=RequestMethod.POST)
	public JSONResult getCategories(@RequestParam(value="blogNo", required=true) Long blogNo) {
		List<CategoryVo> list = categoryDao.selectList(blogNo);
		return JSONResult.success(list);
	}
	
}
