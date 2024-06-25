<!-- ユーザー情報 -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User" %>

<%
User user = (User)session.getAttribute("user");

//セッション切れ
if (user == null) {
	request.setAttribute("error","セッション切れの為、 画面が表示できませんでした。");
	request.setAttribute("cmd","list");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>