package org.zerock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;



//RestController 샘플
@RestController            //해당컨트롤러의 모든 뷰 처리가 JSP가 아니라는 것을 의미합니다.  @ResponseBody 없어도 동일하게 동작 생략되었다고해도 무방
@RequestMapping("/sample")
public class SampleController {
	
	@RequestMapping("hello")
	public String sayHello() {
		return "hello wordl";
	}
	
	//JSON 테스트
	@RequestMapping("sendVO")
	public SampleVO sendVO() {
		
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(123);
		
		return vo;
		
	}
	//List 타입의 JSON 테스트  [{"mno":0,"firstName":"길동","lastName":"홍"} .............
	@RequestMapping("/sendList")
	public List<SampleVO> sendList() {
		
		List<SampleVO> list = new ArrayList<>(); //JDK 1.7이상의 경우 new ArrayList<SampleVO>로 작성안해도됨 제네릭 타입 명시 X
		for(int i = 0; i < 10; i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			list.add(vo);
		}
		return list;
	}
	//Map 타입의 JSON 테스트 {"0":{"mno":0,"firstName":"길동","lastName":"홍"}
	// MAP 의 경우 JavaScript의 JSON 형식으로 보여지게 됩니다. 키(key)와 값(value)으로 구성되어 '{}'로 표현합니다.
	@RequestMapping("/sendMap")
	public Map<Integer, SampleVO> sendMap() {
		
		Map<Integer, SampleVO> map = new HashMap<>();
		
		for(int i = 0; i < 10; i++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			map.put(i, vo);
		}
		return map;
	}
	
	
	/* ResponseBody 타입 HTTP 상태 코드
	 * 100번대: 현재 데이터의 처리 중인 상태
	 * 200번대: 정상적인 응답   200: 에러 없이 정상 처리 , 204: 정상 처리되었으나 서버에서 보내줄 데이터가 없음
	 * 300번대: 301:요청된 페이지가 새 URL로 변경되었음 , 304: 이미 기존의 데이터와 변경된 것이 없음
	 * 400번대: 서버에서 인식할 수 없음 , 
	 *         400:전송된 Request에 문제가 있어서 서버가 인식할 수 없음
	 *         403: 서버에서 허락되지 않음, 404:URL에 해당하는 자원을 찾을 수없음 406: 전송 방식이 허락되지 않음(REST에서 자주 발생)
	 * 500번대: 서버내부의 문제
	 *         502: 게이트웨이나 프록시 상태의 문제(과부하 등)
	 *         503: 일시적인 과부하나 서비스 중단 상태
	 *         504: 지정된 처리시간이 지나서 처리되지 못함   
	 */	
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth() { //스프링에서 제공하는 ResponseEntity 타입은 개발자가 직접 결과 데이터 + HTTP의 상태 코드를 직접 제어 할 수 있는 클래스입니다.
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/sendErrorNot")
	public ResponseEntity<List<SampleVO>> sendListNot() {
		
		List<SampleVO> list = new ArrayList<SampleVO>();
		for(int i = 0; i <10; i ++) {
			SampleVO vo = new SampleVO();
			vo.setFirstName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			list.add(vo);
		}
		return new ResponseEntity<List<SampleVO>>(list, HttpStatus.NOT_FOUND);
	}

}
