package com.cafe24.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	SqlSession sqlSession;

	public boolean insertUser(UserVo userVo) {
		
		int count = sqlSession.insert("user.insert", userVo);

		
		return count == 1;
	}
	
	public UserVo selectUser(UserVo userVo) {
		
		UserVo authUser = sqlSession.selectOne("user.selectUser", userVo);
		
		
		return authUser;
	}
	
	public UserVo selectUser(String id) {
		UserVo vo = sqlSession.selectOne("user.selectUserById", id);
		
		return vo;
	}

}
