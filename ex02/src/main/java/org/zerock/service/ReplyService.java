package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyService {
	
	// 해당게시글에 댓글 모두 불러오기
	public List<ReplyVO> listReply(Integer bno) throws Exception; 
	
	// 해당게시글에 댓글 달기
	public void addReply(ReplyVO vo)throws Exception;

	//댓글 수정
	public void modifyReply(ReplyVO vo)throws Exception;
	
	//댓글 삭제
	public void removeReply(Integer rno) throws Exception;
	
	//댓글 리스트 불러오기 (페이징처리)
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception;
	
	//해당 게시글의 댓글 총합
	public int count(Integer bno) throws Exception;
	
}
