<%-- 注文情報入力画面 --%>

<%@page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<title>注文情報入力画面</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!-- ヘッダー部分 -->
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/userInfo.jsp"%>

		<!-- メニュー部分 -->
		<div id="menu">
			<div class="container">
				<!-- ナビゲーション  -->
				<div id="navList">
					<p class="bold">
						<a href="<%=request.getContextPath()%>/view/list.jsp">商品一覧</a>
					</p>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>注文情報入力</h2>
				</div>
			</div>
		</div>

		<!-- 情報入力コンテンツ部分 -->
		<div id="main" class="container">

			<!--  入力フォーム -->
			<form action="<%=request.getContextPath()%>/view/orderConfirm.jsp">
				<table class="input-table" align="center">
					<tr>
						<th>氏名</th>
						<td><input type="text" name="name"></td>
					</tr>
					<tr>
						<th>メールアドレス</th>
						<td><input type="text" name="Email"></td>
					</tr>
					<tr>
						<th>住所</th>
						<td><input type="text" name="address"></td>
					</tr>
					<tr>
						<th>備考欄</th>
						<td><textarea name="remarks" rows="3" cols="45"></textarea></td>
					</tr>
				</table>

				<input type="submit" value="注文内容確認へ" class="botan">
			</form>

		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>
