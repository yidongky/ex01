package org.zerock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject
	private ReplyDAO dao;

	
	// 댓글 불러오기
	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		
		return dao.list(bno);
	}
	// 댓글 쓰기
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		dao.create(vo);
		
	}
	
	// 댓글 수정
	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		dao.update(vo);
	}
	
	//댓글 삭제
	@Override
	public void removeReply(Integer rno) throws Exception {
		dao.delete(rno);
	}
	
	//댓글 리스트 불러오기 (페이징처리)
	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
		
		return dao.listPage(bno, cri);
	}
	
	//해당 게시글에 댓글 총합
	@Override
	public int count(Integer bno) throws Exception {
		
		return dao.count(bno);
	}

}
