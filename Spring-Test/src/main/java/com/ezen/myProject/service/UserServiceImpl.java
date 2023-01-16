package com.ezen.myProject.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezen.myProject.domain.UserVO;
import com.ezen.myProject.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Inject
	private UserDAO userDao;
	@Inject
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public boolean signUp(UserVO user) {
		logger.info(">>> signup check 2");
		//아이디가 중복되면 회원가입 실패
		//아이디와 일치하는 정보를 db에서 가져옴
		UserVO tmpUser = userDao.getUser(user.getId());
		// tmpUser가 null이 아니라면 이미 가입된 화면 => 아이디 중복 => 회원가입 실패
		if(tmpUser !=null) {
			return false;
		}
		//아이디가 중복되지 않았으면 회원가입
		//아이디가 규칙(유효성 검사) 체크가 맞지 않으면 실패
		//아이디 유효성 검사: 아이디가 입력되었는지만 체크
		if(user.getId()==null || user.getId().length()==0) {
			return false;
		}
		
		//비밀번호 유효성 검사:비밀번호가 입력되었는지만 체크
		if(user.getPw()==null||user.getPw().length()==0) {
			return false;
		}
		
		//회원가입
		//비밀번호를 암호화 과정
		String pw = user.getPw();
		//encode(암호화) / matches(원래비번, 암호화된 비번)
		String encodePw = passwordEncoder.encode(pw);//암호화된 패스워드
		user.setPw(encodePw);
		
		//회원가입 => insert
		int isOk= userDao.insertUser(user);
		return isOk>0? true:false;
	}

	@Override
	public UserVO isUser(String id, String pw) {
		UserVO user = userDao.getUser(id);//앞에서 했던 메서드 호출
		//가져온 user 객체의 비밀번호와 입력받은 비밀번호가 같은지 확인
		//user가 없을 때
		if(user == null) { return null;}
		
		//matches(원래비번, 암호화된비번
		if(passwordEncoder.matches(pw, user.getPw())){//원래비번은 내가 입력받은 비밀번호, 암호화된 비번은 user에서 갖고온 비번
			return user;
		}else {
			return null;			
		}
	}

	@Override
	public UserVO getUser(HttpServletRequest request) {
		UserVO user = (UserVO)request.getSession().getAttribute("ses");
		return user;
	}
	
}
