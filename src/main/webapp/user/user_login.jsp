﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<body>

	
	<jsp:include page="../include/header.jsp"></jsp:include>
	
	
	
	<div align="center" class="div_center">
		
		
			<h3>로그인페이지</h3>
			<hr>
			<form action="" method="post">
				<input type="text" name="id" placeholder="아이디"><br><br>
				<input type="password" name="pw" placeholder="비밀번호"><br><br>
				<input type="submit" value="로그인" class="btn btn-default">&nbsp;&nbsp;
				<input type="button" value="회원가입" class="btn btn-default" onclick="">    
			</form>										
		
			
		
	</div>


	<jsp:include page="../include/footer.jsp"></jsp:include>


</body>
</html>