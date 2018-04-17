package com.cafe24.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.UserVo;

@Repository
public class BlogDao {

	@Autowired
	SqlSession sqlSession;
	
	public boolean insertBlog(UserVo user) {		
		int count = sqlSession.insert("blog.insertNewBlog", user);
		
		return count == 1;
	}
	
	public BlogVo selectBlog(String id) {
		BlogVo blog = sqlSession.selectOne("blog.selectBlogById", id);
		
		
		return blog;
	}
	
	public int updateBlogInfo(BlogVo blog) {
		return sqlSession.update("blog.updateBlogInfo", blog);
	}
	
}
