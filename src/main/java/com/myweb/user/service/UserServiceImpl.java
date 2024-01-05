package com.myweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class UserServiceImpl implements UserService {
	
	private UserDAO dao = UserDAO.getInstance();
	
	@Override
	public int join(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		
		//가입에 대한 프로세스? -> id가 존재하는지 확인 -> insert
		int result = dao.idCheck(id);
		
		if(result == 1) { //아이디 중복
			return 1;
		} else { //회원가입
			UserVO vo = new UserVO(id, pw, name, email, address, gender, null);
			dao.insertUser(vo);
			
			return 0;
		}
		
		
		
		
		
	}

	@Override
	public UserVO login(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		UserVO vo = dao.login(id, pw);
		return vo;
	}

}
