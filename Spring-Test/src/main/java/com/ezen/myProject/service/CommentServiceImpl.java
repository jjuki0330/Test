package com.ezen.myProject.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.myProject.domain.CommentVO;
import com.ezen.myProject.repository.CommentDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	@Inject
	private CommentDAO cdao;

	@Override
	public int register(CommentVO cvo) {
		log.info("comment service check 2");
		return cdao.insertComment(cvo);
	}

	@Override
	public List<CommentVO> getList(int bno) {
		log.info("comment service list 2");
		return cdao.selectList(bno);
	}

	@Override
	public int modify(CommentVO cvo) {
		log.info("comment service modify 2");
		return cdao.modify(cvo);
	}

	@Override
	public int delete(int cno) {
		log.info("comment service delete 2");
		return cdao.delete(cno);
	}
	
}
