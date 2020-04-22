package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

@RestController   //JSP와 같은 뷰를 만들어 내지 않는 대신에 데이터 자체를 반환 주로사용되는 것은 단순 문자열과 JSON,XML 등으로 나누어 볼 수있다.
@RequestMapping("/replies")
public class ReplyController {
	
	/*
	 * @RestController 뷰를 만들어 내지 않고 데이터 자체를 변환 JSON,xml
	 * @ResponseEntity 결과 데이터 + HTTP의 상태코드
	 * @RequestBody  @ModelAttribute 유사하지만 JSON에서사용됨
	 * @PathVariable URI 원하는 데이터 추룰 (파라메터 값 등 )
	 */
	
	@Inject
	private ReplyService service;
	//댓글 작성
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) {  //@ResponseEntity 타입은 개발자가 직접 결과 데이터 + HTTP의 상태 코드를 직접 제어할 수 있는 클래스입니다.
		//@RequestBody : 전송된 JSON 데이터를 객체로 변환해주는 애노테이션 으로 @ModelAttribute 유사한 역할을 하지만 JSON에서 사용된다는 점이 차이 
		
		ResponseEntity<String> entity = null; //선언
		try {
			
			service.addReply(vo); //댓글 작성하기
			entity = new ResponseEntity<String>("success", HttpStatus.OK); //성공시 성공 메시지 전송
			
		}catch (Exception e) {			
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); //새로운 댓글 등록하는데 실패시 	BAD_REQUEST(400)을 결과로 전송	
		}
		return entity;
	}
	//댓글 리스트 보기
	@RequestMapping(value = "/all/{bno}", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") Integer bno) { //@PathVariable URL 경로에서 원하는 데이터를 추출하는 용도로 사용 
		
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try {
			entity = new ResponseEntity<>(service.listReply(bno), HttpStatus.OK); //성공시 JSON 데이터 전송
			
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST); //실패시 400 에러메시지 전송
		}
		return entity;
	}
	//수정 처리 REST 방식에서 update 작업은 PUT, PATCH 방식을 이용해서 처리합니다. 일반적으로 전체 데이터를 수정하는 경우 PUT을 이용하고, 일부의 데이터를 수정하는경우 PATCH를 이용합니다.
	@RequestMapping(value = "/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno, @RequestBody ReplyVO vo){
		
		ResponseEntity<String> entity =null;
		//댓글의 내용은 application/json 타입으로 전송
		
		try {
			vo.setRno(rno); // 수정할 댓글 번호 삽입
			service.modifyReply(vo); //댓글 수정
			
			entity = new ResponseEntity<String>("succes",HttpStatus.OK); // http 상태 메시지: 성공
			
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(
					e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
		return entity;
	}
	//삭제처리
	@RequestMapping(value = "/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") Integer rno){
		ResponseEntity<String> entity = null;
		try {
				service.removeReply(rno);
				entity = new ResponseEntity<String>("succes",HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	//페이징처리 댓글 불러오기
	@RequestMapping(value = "/{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") Integer bno, @PathVariable("page") Integer page){
							// 키, value
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			Criteria cri = new Criteria();
			cri.setPage(page); //페이저 번호 적용
			
			PageMaker pageMaker = new PageMaker(); 
			pageMaker.setCri(cri); //시작페이지 마지막페이지 이전페이지 다음페이지 계산
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<ReplyVO> list = service.listReplyPage(bno, cri);
			
			map.put("list", list);
			
			int replyCount = service.count(bno);
			pageMaker.setTotalCount(replyCount);
			
			map.put("pageMaker",pageMaker);
			
			entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
			
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
}
