<%@page contentType="text/html; charset=UTF-8"%>

<%
//エラー情報を取得
String error = (String) request.getAttribute("error");
String cmd = (String) request.getAttribute("cmd");
%>

<html>
<head>
<title>エラー画面</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">
		<!-- ヘッダー部分 -->
		<%@ include file="/common/header.jsp"%>

		<!-- メニュー部分 -->
		<div id="menu">
			<div class="container">
				<!-- ページタイトル -->
				<div id="page_title">
					<h2>■■エラー■■</h2>
				</div>
			</div>
		</div>

		<!-- コンテンツ部分 -->
		<div id="main" class="container">

			<p class="error-msg"><%=error%></p>

			<p class="back-link">
				<%
				switch (cmd) {
				case "list":
				%>
				<a href="<%=request.getContextPath()%>/list">【商品一覧画面へ戻る】</a>
				<%
				break;
				case "orderedList":
				%>
				<a href="<%=request.getContextPath()%>/orderedList">【受注一覧画面へ戻る】</a>
				<%
				break;
				case "login":
				%>
				<a href="<%=request.getContextPath()%>/logout">【ログイン画面へ戻る】</a>
				<%
				break;
				case "insertProfile":
				%>
				<a href="<%=request.getContextPath()%>/view/insertProfile.jsp">【会員登録画面へ戻る】</a>
				<%
				break;
				case "orderConfirm":
				%>
				<a href="<%=request.getContextPath()%>/view/orderConfirm.jsp">【注文内容確認画面へ戻る】</a>
				<%
				break;
				case "insert":
				%>
				<a href="<%=request.getContextPath()%>/view/insert.jsp">【商品登録画面へ戻る】</a>
				<%
				break;
				}
				%>
			
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>