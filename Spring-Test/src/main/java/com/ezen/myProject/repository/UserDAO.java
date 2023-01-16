package com.ezen.myProject.repository;

import com.ezen.myProject.domain.UserVO;

public interface UserDAO {

	UserVO getUser(String id);

	int insertUser(UserVO user);

	UserVO getUsers(UserVO attribute);

	
}
