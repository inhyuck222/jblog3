package com.cafe24.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.PostVo;
import com.cafe24.jblog.vo.UserVo;

@Service
public class PostService {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	BlogDao blogDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	public boolean writePost(PostVo post, UserVo authUser) {
		BlogVo blog = blogDao.selectBlog(authUser.getId());
		post.setBlogNo(blog.getNo());
		boolean result = postDao.insertPost(post);
		categoryDao.updatePostNo(post.getCategoryNo());
		
		return result;
	}
	
	public List<PostVo> getPostList(Long blogNo){
		List<PostVo> postList = postDao.selectPostList(blogNo);
		
		return postList;
	}
	
	public List<PostVo> getPostList(Long blogNo, Long categoryNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blogNo", blogNo);
		map.put("categoryNo", categoryNo);
		List<PostVo> postList = postDao.selectPostList(map);
		
		return postList;
	}
	
	public PostVo getPost(Long postNo) {
		PostVo post = postDao.selectThePost(postNo);
		return post;
	}
	
	public boolean deletByCategory(Long categoryNo) {
		boolean result = postDao.deletByCategory(categoryNo);
		
		return result;
	}

}
