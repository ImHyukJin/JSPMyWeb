package com.myweb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserVO;
import com.myweb.user.service.UserService;
import com.myweb.user.service.UserServiceImpl;

@WebServlet("*.user") //확장자패턴
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
		
		//3. URL주소를 분기(각 요청별로 코드를 만듬)
		request.setCharacterEncoding("utf-8"); //한글처리
		
		String uri = request.getRequestURI();
		String path = uri.substring( request.getContextPath().length()  );
		
		System.out.println(path);
		
		
		//서비스 영역 선언
		UserService service = new UserServiceImpl();
		
		
		if(path.equals("/user/join.user")) { //가입화면
			
			//화면이동 기본값 - forward형식이 되어야함
			request.getRequestDispatcher("user_join.jsp").forward(request, response);

		} else if(path.equals("/user/login.user") ) { //로그인화면

			request.getRequestDispatcher("user_login.jsp").forward(request, response);
		
		} else if(path.equals("/user/joinForm.user") ) { //회원가입
			
			//String id = request.getParameter("id");
			//.....
			int result = service.join(request, response);
			
			if(result == 1) { //아이디중복
				request.setAttribute("msg", "아이디가 중복되었습니다");
				request.getRequestDispatcher("user_join.jsp").forward(request, response);
			} else { //회원가입성공
				response.sendRedirect("login.user"); //MVC2 방식에서 리다이렉트는 다시 컨트롤러를 태우는데 사용함.
			}
			
		} else if(path.equals("/user/loginForm.user")) { //로그인
			
			UserVO vo = service.login(request, response);
			if(vo != null) { //로그인 성공
				//서블릿에서는 request.getSession 현재세션을 얻을 수 있습니다.
				HttpSession session = request.getSession();
				session.setAttribute("user_id", vo.getId() );
				session.setAttribute("user_name", vo.getName() );
				
				response.sendRedirect( request.getContextPath() ); //홈화면
				
			} else { //로그인 실패
				request.setAttribute("msg", "아이디 비밀번호를 확인하세요");
				request.getRequestDispatcher("user_login.jsp").forward(request, response);
			}
			
		
		} else if(path.equals("/user/logout.user")) { //로그아웃
			HttpSession session = request.getSession();
			session.invalidate();
			
			response.sendRedirect( request.getContextPath() ); //홈화면
				
		} else if(path.equals("/user/mypage.user") ) { //마이페이지 화면
			
			request.getRequestDispatcher("user_mypage.jsp").forward(request, response);
		
		} else if(path.equals("/user/update.user")) { //정보수정화면
			
			request.getRequestDispatcher("user_update.jsp").forward(request, response);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	
}
