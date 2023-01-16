package com.ezen.myProject;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ezen.myProject.domain.BoardVO;
import com.ezen.myProject.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)//테스트 수행시 항상 사용
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardTest {
	
	@Inject
	private BoardDAO bdao;
	
	@Test //테스트에서 사용되는 메서드라는 표시
	public void insertBoard() {
		BoardVO bvo = new BoardVO();
		
		bvo.setTitle("Test Title");
		bvo.setContent("TestContent");
		bvo.setWriter("tester");
		
		int isOk =bdao.insertBoard(bvo);
		log.info("insert Test>>> "+ (isOk>0? "OK":"FAIL"));
	}
	
	@Test
	public void selectBoard() {
		BoardVO bvo = new BoardVO();
		bvo = bdao.selectBoardDetail(1);
		
		log.info("select test>> "+bvo.toString());
		
		
	}
}
