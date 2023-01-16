package com.ezen.myProject.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingVO {
	
	private int pageNo;//화면에 출력된 페이지네이션 번호 1,2,3,..,n
	private int qty;//한페이지당 보여줘야하는 게시글 수 
	
	private String type;
	private String keyword;
	
	//기본값을 주는 경우에은 생성자를 롬복으로 생성할 수 없음
	public PagingVO() {
		this(1,10);
	}
	public PagingVO(int pageNo, int qty) {
		this.pageNo=pageNo;
		this.qty=qty;
	}
	//처음 스타트 번호를 반환해주는 메서드
	public int getPageStart() {
		return (this.pageNo-1)*qty; //DB에서 값을 limit 첫 시작이 0번지
	}
	
	//??????
	public String[] getTypeToArray() {
		return this.type == null ? new String [] {}: this.type.split("");//값이 있으면 하나씩 쪼개서 배열로 만들어 주겠다
	}
	
	

}
