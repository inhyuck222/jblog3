package com.cafe24.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVo;

@Repository
public class PostDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public boolean insertPost(PostVo post) {
		int count = sqlSession.insert("post.insertPost", post);
		
		return count == 1;
	}
	
	public List<PostVo> selectPostList(Long blogNo){
		List<PostVo> list = sqlSession.selectList("post.selectPostList", blogNo);
		
		return list;
	}
	
	public List<PostVo> selectPostList(Map<String, Object> map){
		List<PostVo> list = sqlSession.selectList("post.selectPostListByCategory", map);
		
		return list;
	}
	
	public PostVo selectThePost(Long postNo) {
		PostVo post = sqlSession.selectOne("post.selectThePost", postNo);
		
		return post;
	}
	
	public boolean deletByCategory(Long categoryNo) {
		int count = sqlSession.delete("post.deletByCategory", categoryNo);
		
		return count == 1;
	}

}
