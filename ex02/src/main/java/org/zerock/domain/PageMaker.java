package org.zerock.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private int displayPageNum = 10; //보여주는 페이지수 
	
	private Criteria cri; //페이징 처리 클래스

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) { // 총자료수 설정되는 시점에 calcData()실행해서 계산
		this.totalCount = totalCount;	
		calcData();
	}
	private void calcData() {
		endPage = (int) (Math.ceil(cri.getPage()/(double)displayPageNum) * displayPageNum); //마지막페이지
		//                  올림(현재 페이지수 / 보여주는페이지 번호 수) * 보여주는페이지 번호수
		startPage = (endPage - displayPageNum) + 1;  // 마지막페이지 - 보여주는페이지 + 1
		
		int tempEndPage = (int) (Math.ceil(totalCount/(double) cri.getPerPageNum()));
		//보여주는 마지막페이지   = 올림(게시글총갯수/페이지당 보여주는 게시글 수)
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = startPage == 1 ? false : true; //이전페이지
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true; //마지막페이지
	}

	public int getTotalCount() {
		return totalCount;
	}



	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public String MakeQuery(int page) {
       //URI를 작성할때 도움이 되는 클래스가 UriComponentsBuilder 와 UriComponents 클래스 입니다. GET방식으로 유지해야 하는 경우 처리과정이 복잡해질 때 주로사용
		UriComponents uriComponents = UriComponentsBuilder.newInstance().queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum()).build();

		return uriComponents.toUriString();
	}

	public String makeSearch(int page) {
		//URI를 작성할때 도움이 되는 클래스가 UriComponentsBuilder 와 UriComponents 클래스 입니다. GET방식으로 유지해야 하는 경우 처리과정이 복잡해질 때 주로사용
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("searchType", ((SearchCriteria)cri).getSearchType())  //cri SearchCriteria 타입으로 객체 강제형변환
				.queryParam("keyword", ((SearchCriteria)cri).getKeyword())
				.build();
		
		return uriComponents.toUriString();
	}

	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}
	
	
	
	
}
