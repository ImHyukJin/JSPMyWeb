package com.myweb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.user.service.UserService;
import com.myweb.user.service.UserServiceImpl;

@WebServlet("*.user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//3. URL주소를 분기(각 요청별로 코드를 만듦)
		request.setCharacterEncoding("utf-8");

		String uri = request.getRequestURI();
		String path = uri.substring(request.getContextPath().length());
		
		System.out.println(path);
		//404가 뜨면 확장자 경로가 잘못되었거나 들어오는 또는 나가는 경로가 잘못되었다. if와 forward가 잘못됨 또는 webservlet이 잘못
		
		//서비스 영역 선언
		UserService service = new UserServiceImpl();	
		
		if(path.equals("/user/join.user")) {
			//화면으로 이동 기본값 -forward형식이 되어야함
			request.getRequestDispatcher("user_join.jsp").forward(request, response);//가입화면
		}else if(path.equals("/user/login.user")) {
			request.getRequestDispatcher("user_login.jsp").forward(request, response); //로그인화면
		}else if(path.equals("/user/joinForm.user")) {
			//String id = request.getParameter("id");
			//...
			
			int result = service.join(request, response);
			System.out.println("실행 결과 : " + result );
		}
		
	}
}
