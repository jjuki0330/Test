package com.ezen.myProject.repository;

import java.util.List;

import com.ezen.myProject.domain.BoardVO;
import com.ezen.myProject.domain.PagingVO;

public interface BoardDAO {

	int insertBoard(BoardVO bvo);

	List<BoardVO> selectBoardList();

	BoardVO selectBoardDetail(int bno);

	void readCount(int bno);

	int updateBoard(BoardVO bvo);

	int delete(int bno);

	List<BoardVO> selectBoardListPaging(PagingVO pvo);

	int selectBoardSize();

	int searchTotalCount(PagingVO pvo);

	int selectOneBno();

}
