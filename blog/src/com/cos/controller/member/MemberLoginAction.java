package com.cos.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.action.Action;
import com.cos.dao.MemberDAO;
import com.cos.dto.MemberVO;
import com.cos.util.SHA256;
import com.cos.util.Script;

public class MemberLoginAction implements Action{
	private static String naming ="MemberLoginAction : ";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "main.jsp";
		
		MemberVO member = new MemberVO();
		MemberDAO dao = new MemberDAO();
		
		String id = null;
		String password = null;
		String salt = null;
		
		if(request.getParameter("id") != null) { 
			id = request.getParameter("id");
		}
		if(request.getParameter("password") != null) { 
			salt = dao.select_salt(id);
			password = request.getParameter("password");
			//패스워드를 SHA256으로 해쉬하기
			password = SHA256.getEncrypt(password, salt);  //getEncrypt 를 호출하면 해쉬값으로 바뀐다.
		}
	
		member.setId(id);
		member.setPasswoard(password);
		
		int result = dao.login(member);
		
		if(result ==1) {
			HttpSession session = request.getSession();
			session.setAttribute("id", member.getId());
			Script.moving(response, "로그인 성공",url);
		
		}else if(result == 2) {
			HttpSession session = request.getSession();
			session.setAttribute("id", member.getId());
			Script.moving(response, "이메일 인증이 되지 않았습니다. 인증을 해주세요.",url);
		}
		else if(result == -1) {
			Script.moving(response, "데이터베이스 에러");
		}
	}
	
	

}
