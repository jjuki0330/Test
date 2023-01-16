package com.ezen.myProject.handler;

import com.ezen.myProject.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PagingHandler {

	private int startPage;//현재 화면에서 보여줄 시작 페이지네이션 번호
	private int endPage;//현재 화면에서 보여줄 마지막 페이지네이션 번호
	private boolean prev,next;//이전, 다음 페이지 여부
	private int pgQty=5;
	
	// 총 10개씩 보이게
	// 1 2 3 4 5 6 7 8 9 10(endPage) next
	// prev 11 12 13 14 15 16 17 18 19 20 next
	
	private int totalCount;//전체 게시글 수
	private PagingVO pgvo;
	
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo=pgvo;
		this.totalCount=totalCount;
		this.endPage =(int)Math.ceil(pgvo.getPageNo()/(pgQty*1.0))*pgQty;
		this.startPage=endPage-(pgQty-1);
		
		int realEndPage=(int)Math.ceil((totalCount*1.0)/pgvo.getQty());
		
		if(realEndPage <this.endPage) {
			this.endPage = realEndPage;
		}
		this.prev=this.startPage>1;
		this.next = this.endPage<realEndPage;
	}
}

