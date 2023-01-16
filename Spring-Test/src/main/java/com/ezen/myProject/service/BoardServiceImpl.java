package com.ezen.myProject.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.myProject.domain.BoardDTO;
import com.ezen.myProject.domain.BoardVO;
import com.ezen.myProject.domain.FileVO;
import com.ezen.myProject.domain.PagingVO;
import com.ezen.myProject.domain.UserVO;
import com.ezen.myProject.repository.BoardDAO;
import com.ezen.myProject.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO bdao;
	@Inject
	private FileDAO fdao;

	@Override
	public int register(BoardVO bvo) {
		log.info(">>> board register check 2");
		return bdao.insertBoard(bvo);
	}

	@Override
	public List<BoardVO> getList() {
		log.info(">>> board list check 2");
		return bdao.selectBoardList();
	}

	@Override
	public BoardVO getDetail(int bno) {
		log.info(">>> board detail check 2");
		bdao.readCount(bno);
		return bdao.selectBoardDetail(bno);
	}

	@Override
	public int modify(BoardVO bvo, UserVO user) {
		log.info(">>> board modify check 2");
		BoardVO tmpBoard = bdao.selectBoardDetail(bvo.getBno());//이 bno에 해당하는 정보를 가져와바
		if(user == null || !user.getId().equals(tmpBoard.getWriter())) {
			return 0;
		}
		return bdao.updateBoard(bvo);
	}

	@Override
	public int getDelete(int bno, UserVO user) {
		log.info(">>> board delete check 2");
		BoardVO tmpBoard = bdao.selectBoardDetail(bno);
		if(user == null || !user.getId().equals(tmpBoard.getWriter())) {
			return 0;
		}
		return bdao.delete(bno);
	}

	@Override
	public List<BoardVO> getList(PagingVO pvo) {
		log.info(">>> board pagingList check 2");
		return bdao.selectBoardListPaging(pvo);
	}

	@Override
	public int getListSize() {
		log.info(">>> board getListSize check 2");
		return bdao.selectBoardSize();
	}

	@Override
	public int getListSize(PagingVO pvo) {
		log.info(">>> board search check 2");
		return bdao.searchTotalCount(pvo);
	}

	@Override
	public int registerFile(BoardDTO bdto) {
		log.info(">>> board registerFile check 2");
		int isOk = bdao.insertBoard(bdto.getBvo());//기존 게시글에 대한 내용을 db에 저장
		if(isOk >0 && bdto.getFList().size()>0) {
			//가장 큰 bno 가져오기
			int bno =bdao.selectOneBno();//방금 넣었던 bvo 객체가 db에 저장된 후 
			for(FileVO fvo: bdto.getFList()) {
				fvo.setBno(bno);
				log.info("insert file>> "+fvo.toString());
				isOk *=fdao.insertFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public BoardDTO getDetailFile(int bno) {
		log.info(">>> board getDetailFile check 2");
		bdao.readCount(bno);//detaill 선택 시 조회수 증가 메서드
		BoardDTO bdto = new BoardDTO(bdao.selectBoardDetail(bno), fdao.selectFileList(bno));
		
		return bdto;
	}

	@Override
	public int modifyFile(BoardDTO bdto) {
		log.info(">>> board modifyFile check 2");
		int isOk=0;
		if(bdto.getFList().size()>0) {
			//가장 큰 bno 가져오기
			int bno =bdto.getBvo().getBno();//방금 넣었던 bvo 객체가 db에 저장된 후 
			for(FileVO fvo: bdto.getFList()) {
				fvo.setBno(bno);
				log.info("insert file>> "+fvo.toString());
				isOk *=fdao.insertFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public int delete(String uuid) {
		log.info(">>> board deleteFile check 2");
		return fdao.deleteFile(uuid);
	}
	
}
