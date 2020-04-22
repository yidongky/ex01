package org.zerock.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "org.zerock.mapper.replyMapper";
	
	//해당게시글에 댓글 모두 보기
	@Override
	public List<ReplyVO> list(Integer bno) throws Exception {
		
		return session.selectList(namespace + ".list", bno);
	}
	//댓글 작성하기
	@Override
	public void create(ReplyVO vo) throws Exception {
		session.insert(namespace + ".create", vo);
	}
	//댓글 수정하기
	@Override
	public void update(ReplyVO vo) throws Exception {
		
		session.update(namespace + ".update" ,vo);
	}
	//댓글 삭제하기
	@Override
	public void delete(Integer rno) throws Exception {
		
		session.delete(namespace + ".delete", rno);
	}
	//댓글 목록불러오기
	@Override
	public List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception {
		
		Map<String, Object> ParamMap = new HashMap<>(); //XML의 reuslttype 떄문에 MAP 담아서 
		ParamMap.put("bno", bno);
		ParamMap.put("cri", cri);
		
		return session.selectList(namespace + ".listPage" ,ParamMap);
	}
	//해당게시글에 댓글 총합
	@Override
	public int count(Integer bno) throws Exception {
		
		return session.selectOne(namespace + ".count" ,bno);
	}

}
