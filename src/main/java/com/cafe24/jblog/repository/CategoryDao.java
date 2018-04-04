package com.cafe24.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public List<CategoryVo> selectList(Long blogNo){
		List<CategoryVo> list = sqlSession.selectList("category.selectByBlogNo", blogNo);
		
		return list;
	}
	
	public boolean insertDefaultCategory(Long blogNo) {
		int count = sqlSession.insert("category.insertDefault", blogNo);
		
		return count == 1;
	}
	
	public boolean insertNewCategory(CategoryVo category) {
		int count = sqlSession.insert("category.insertNewCategory", category);
		System.out.println(category.getNo());
		return count == 1;
	}
	
	public boolean delete(Long categoryNo) {
		int count = sqlSession.delete("category.delete", categoryNo);
		
		return count == 1;
	}
	
	public boolean updatePostNo(Long categoryNo) {
		int count = sqlSession.update("category.updatePostNo", categoryNo);
		
		return count == 1;
	}

}
