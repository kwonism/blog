<%@page import="com.cos.dao.MemberDAO"%>
<%@page import="com.mysql.fabric.xmlrpc.base.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<body>
   <%
      session.invalidate();
   %>
   <script>
      location.href="<%=request.getContextPath()%>/main.jsp"
   </script>
</body>
</html>