package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.quizwiki.dto.MemberDTO;

//기능별로 파일 나누고싶은데 일단 몇개없으니 Utils파일에 다 때려박앗씁니당..
public class Utils {
	
	/***
	 * 마이바티스로 페이징처리할때 sql맵퍼에서 limit동적으로 받아올때 파라미터로 넘길 Map에 키밸류 추가
	 * @param 맵퍼에 넘길 map , size = 페이지에 보여줄 갯수 , page = 현재 페이지인덱스(1부터시작)
	 * @return 멤버DTO가 필요하지 않지만 로그인체크는 해야되는경우, true or false 리턴
	 */
	public static void addPageParam(Map<String,Object> map, int size , int page) {
		int startIndex = (page-1)*size;
		map.put("startLimit", startIndex);
		map.put("sizeLimit", size);
	}
	
	/***
	 * 페이징처리할때 페이징버튼(1 2 3 4 5 이런거) 만들떄 전체 갯수에서 12345를 담은 intList를 반환해요
	 * @param n = 한번에 보여줄 페이징버튼int숫자 (3이면 1 2 3 이렇게 3개씩), size=한페이지에 보여줄 갯수 , max = 전체 객체수(?)
	 * @return 페이징 처리에 필요한 페이징버튼 list를 반환해줘요
	 */
	public static List<Integer> makePagingSeq(int n, int size , int max){
		List<Integer> seq = new ArrayList<Integer>();
		int pageIndex = (n-1)/size;
		int start = pageIndex*size;
		for(int i= start+1; i<= start+size; i++) {
			System.out.println("i==>"+i);
			System.out.println("max==>"+max);
			if(i> max) break;
			seq.add(i);
		}
		
		return seq;
	}
	
	
	/***
	 * 전체 객체의숫자(count) , 한페이지에 보여줄 객체 숫자(size)로 전체 페이지수를 반환해줘요
	 * @param count = 전체 객체의 숫자 , size = 전체 페이지 수
	 * @return 페이징 처리에 필요한 페이징버튼 list를 반환해줘요
	 */
	public static int getTotalPage(int count , int size) {
		int result = (int)Math.ceil(count/size);
		
		return result<=0 ? 1 : result;
	}
	
	
	/***
	 * 멤버DTO가 필요하지 않지만 로그인체크는 해야되는경우, 
	 * @param HttpServletRequest
	 * @return 멤버DTO가 필요하지 않지만 로그인체크는 해야되는경우, true or false 리턴
	 */
	public static boolean loginCheck(HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		Object user = null;
		
		if(session != null) {
			user = session.getAttribute("user");
		}
		
		return (user!=null);
	}
	
	/***
	 * 세션이 있는지 체크하고 멤버DTO를 받아오는작업
	 * @param HttpServletRequest
	 * @return 세션에 정보가있으면 MemberDTO를 없으면 null을 리턴
	 */
	public static MemberDTO getSessionUser(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		MemberDTO user = null;
		
		if(session != null) {
			user = (MemberDTO)session.getAttribute("user");
		}
		
		return user;
	}
	
	
	/***
	 * 객체와 메세지를 담은 JsonNode를 반환해줌.
	 * @param 전달하고싶은 객체
	 * @param 전달하고 싶은 메시지 문자열
	 * @return obj와 msg를 담은 JsonNode 객체 반환
	 */
	public static <T> JsonNode getJsonStringAsResForm(T obj, String msg) {
		ObjectMapper mapper = new ObjectMapper();
		String data = "error";
	    try {
	    	data = mapper.writeValueAsString(obj);
	    }catch(JsonProcessingException e){
	    	e.printStackTrace();
	    }
	    
	    JsonNode jnode = mapper.createObjectNode()
                .put("data", data)
                .put("msg", msg);
	    return jnode;
	}
	
	
	/***
	 * 로그인이 끝난뒤 redirect될 페이지를 셋팅 기본값이 redirect:이다.
	 * @param session
	 * @param path
	 */
	public static void setSendAfterLogin(HttpSession session, String path) {
		session.setAttribute("sendAfterLogin", ("redirect:"+path));
	}
	
	/***
	 * sendAfterLogin 어트리뷰트를 세션에서 받아온다. 설정된게 없으면 null
	 * @param session
	 * @return
	 */
	public static String getSendAfterLogin(HttpSession session) {
		return (String)session.getAttribute("sendAfterLogin");
	}
}
