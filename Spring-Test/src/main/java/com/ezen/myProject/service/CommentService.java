package com.ezen.myProject.service;

import java.util.List;

import com.ezen.myProject.domain.CommentVO;

public interface CommentService {

	int register(CommentVO cvo);

	List<CommentVO> getList(int bno);

	int modify(CommentVO cvo);

	int delete(int cno);

}
