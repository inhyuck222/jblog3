package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.UserDao;
import com.cafe24.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	public boolean userJoin(UserVo userVo) {

		if ("".equals(userVo.getId()) || "".equals(userVo.getName()) || "".equals(userVo.getPassword())) {
			return false;
		}
		
		boolean result = userDao.insertUser(userVo);
		
		return result;
	}

	public UserVo userLogin(UserVo userVo) {

		if ("".equals(userVo.getId()) || "".equals(userVo.getPassword())) {
			return null;
		}
		
		UserVo result = userDao.selectUser(userVo);
		
		return result;
	}

}
