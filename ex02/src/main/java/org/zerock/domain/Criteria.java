package org.zerock.domain;
/*
 * page: 현재 조회하는 페이지의 번호
 * PerPageNum: 한페이지당 출력하는 데이터의 개수
 * totalCount: SQL의 결과로 나온 데이터의 전체 개수
 * 
 * startPage 시작페이지
 * endPage 마지막페이지
 * prev 이전페이지
 * next 다음페이지
 * */


public class Criteria {
	
	private int page; //시작페이지 
	private int perPageNum; //한페이지에서 몇개씩 보여줄지 설정
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	
	
	public void setPage(int page) {
		if(page <= 0 ) { // 페이지가 0보다 작으면 무조건 1
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) { //표시되는 페이지의 마지막 
		if(perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;
	}
	
	public int getPage() {
		return page;
	}
	
	//method for myBatis SQL Mapper -
	public int getPageStart() { //시작페이지 설정
		return (this.page -1) * perPageNum;
	}
	
	//method for myBatis Sql Mapper
	public int getPerPageNum() {
		return this.perPageNum;
	}


	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
	
	
}
