package com.myweb.board.service;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardDAO;
import com.myweb.board.model.BoardVO;

public class BoardServiceImpl implements BoardService {

	private BoardDAO dao = BoardDAO.getInstance();
	
	
	@Override
	public void regist(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		dao.insert(writer, title, content);
	}


	@Override
	public ArrayList<BoardVO> getList(HttpServletRequest request, HttpServletResponse response) {
		
		ArrayList<BoardVO> list =dao.getList();
		
		return list;
	}


	@Override
	public BoardVO getContent(HttpServletRequest request, HttpServletResponse response) {
		
		String bno = request.getParameter("bno");
		
		BoardVO vo = dao.getContent(bno);
		
		return vo;
	}

//	@Override
//	public int update(HttpServletRequest request, HttpServletResponse response) {
//		
//		String bno = request.getParameter("bno");
//		
//		BoardVO vo = dao.getContent(bno);
//		int result = dao.update(vo);
//				
//		return result;
//	}

	@Override
	public int update(HttpServletRequest request, HttpServletResponse response) {
	
		String bno = request.getParameter("bno");
		
		String title = request.getParameter("title");
		
		String content = request.getParameter("content");
		
		int result = dao.update(bno, title, content);
		
		return result;
	}


	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		
		String bno = request.getParameter("bno");
		dao.delete(bno);
	}


	@Override
	public void hitUpdate(HttpServletRequest request, HttpServletResponse response) {

		//쿠키 or 세션을 사용해서 조회수 중복을 막음
		
		//Cookie coo = new Cookie(키 , 값)
		//coo.setMaxAge(30) --시간설정
		//response.addCookie(coo) <- 클라이언트로 쿠키 넘어감
		
		String bno = request.getParameter("bno");
		
		String coovalue = ""; //쿠키의 초기값
		
		boolean flag = true ; //if문의 실행여부
		//기존쿠기가 있었는지 확인
		Cookie[] arr =request.getCookies();
		
		if(arr != null) {
			for(Cookie c : arr) {
				if(c.getName().equals("hit")) { //hit 쿠키가 있다.
					coovalue = c.getValue();
					
					if(c.getValue().contains(bno)) {
						System.out.println(true);
						flag = false ;
					}
				}
			}
		}
		
		if(flag) { // 
			dao.hitUpdate(bno);
			coovalue +=bno +"-";
		}

		
		Cookie coo = new Cookie("hit",coovalue);
		coo.setMaxAge(30);
		response.addCookie(coo);
		
		
	
	}


}
