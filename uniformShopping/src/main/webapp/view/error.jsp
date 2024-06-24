<%@page contentType="text/html; charset=UTF-8"%>

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

			<p class="error-msg">error文</p>

			<p class="back-link">

				<a href ="<%=request.getContextPath()%>/view/login.jsp">[ログイン画面へ]</a> <a href ="<%=request.getContextPath()%>/view/list.jsp">[一覧表示に戻る]</a>

			</p>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>