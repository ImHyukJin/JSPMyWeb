package com.myweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.user.model.UserDAO;

public class UserServiceImpl implements UserService{

	public int join(HttpServletRequest request, HttpServletResponse response) {
			
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String gender = request.getParameter("gender");
			
			System.out.println("넘어온 값: " + id);
			//가입에 대한 프로세스? -> insert 가 올 때 id가 중복인지 아닌지 
			
			UserDAO dao =UserDAO.getInstance();
			int result = dao.idcheck(id);
			
			if(result == 1) { //아이디 중복
				return result ; 
			}else { //회원가입진행
				return 0;
			}
			
			
	}

	
}
