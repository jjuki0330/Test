package com.ezen.myProject.service;

import javax.servlet.http.HttpServletRequest;

import com.ezen.myProject.domain.UserVO;

public interface UserService {

	boolean signUp(UserVO user);


	UserVO isUser(String id, String pw);


	UserVO getUser(HttpServletRequest request);

}
