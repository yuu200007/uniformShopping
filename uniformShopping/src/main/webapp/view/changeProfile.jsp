<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.*,dao.*" %>
<%
User user = (User) session.getAttribute("user");
%>
<html>
	<head>
		<title>会員情報変更</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	</head>
	<body>
		<!-- ブラウザ全体 -->
		<div id="wrap">
			<!-- ヘッダー部分 -->
			<%@ include file="/common/header.jsp" %>

			<!-- メニュー部分 -->
			<div id="menu">
				<div class="container">
					<!-- ナビゲーション  -->
					<div id="nav">
						<ul>
							<li><a href ="<%=request.getContextPath()%>/list">[商品一覧]</a></li>
						</ul>
					</div>

					<!-- ページタイトル -->
					<div id="page_title">
						<h2>会員情報変更</h2>
					</div>
				</div>
			</div>

			<!-- 会員情報変更コンテンツ部分 -->
			<div id="main" class="container">
			
				<!-- 変更画面 -->
				<form action="<%=request.getContextPath()%>/changeProfile" method="post">
					<table class="input-table">
						<thead>
							<tr>
								<td></td>
								<th>&lt;&lt;変更前情報&gt;&gt;</th>
								<th>&lt;&lt;変更後情報&gt;&gt;</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th>ID</th>
								<input type="hidden" name="id" value="<%=user.getLogin_id()%>">
								<td><%=user.getLogin_id()%></td>
								<td><%=user.getLogin_id()%></td>
							</tr>
							<tr>
								<th>パスワード</th>
								<td><%=user.getPassword() %></td>
								<td><input type="text" name="password"></td>
							</tr>
							<tr>
								<th>名前</th>
								<td><%=user.getName() %></td>
								<td><input type="text" name="name"></td>
							</tr>
							<tr>
								<th>住所</th>
								<td><%=user.getAddress() %></td>
								<td><input type="text" name="address"></td>
							</tr>
							<tr>
								<th>メールアドレス</th>
								<td><%=user.getEmail() %></td>
								<td><input type="text" name="Email"><input type="hidden" name="authority" value="2"></td>
							</tr>
						</tbody>
					</table>
					<input type="submit" value="変更完了">
				</form>
			</div>
			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>

		</div>

	</body>
</html>
