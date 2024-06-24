<%@page contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<title>ログイン画面</title>
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
						<h2>ログイン画面</h2>
					</div>
				</div>
			</div>

			<!-- ログインのコンテンツ部分 -->
			<div id="main" class="container">

				<form action="<%=request.getContextPath()%>/login" method="POST">
					<table class="input-table">
						<tr>
							<th>ユーザー</th>
							<td>
								<input type="text" size="25" name="id" value="">
							</td>
						</tr>
						<tr>
							<th>パスワード</th>
							<td>
								<input type="password" size="25" name="password" value="">
							</td>
						</tr>
					</table>
					<input type="submit" value="ログイン">
				</form>
			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>
		</div>
	</body>
</html>