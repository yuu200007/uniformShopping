<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.Item"%>

<html>
<head>
<title>商品登録</title>
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
						<a href="<%=request.getContextPath()%>/orderedList">受注状況一覧</a>
						&nbsp; &nbsp; <a href="<%=request.getContextPath()%>/list">商品一覧</a></
					</p>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>商品登録</h2>
				</div>
			</div>
		</div>

		<!-- 書籍登録コンテンツ部分 -->
		<div id="main" class="container">

			<!--  入力フォーム -->
			<form method="post" enctype="multipart/form-data"
				action="<%=request.getContextPath()%>/insert">
				<table class="input-table" align="center">
					<tr>
						<th>商品名</th>
						<td><input type="text" name="item_name"></td>
					</tr>
					<tr>
						<th>価格</th>
						<td><input type="text" name="price"></td>
					</tr>
					<tr>
						<th>在庫数</th>
						<td><input type="text" name="stock"></td>
					</tr>
					<tr>
						<th>商品画像</th>
						<td><input type="file" name="image"></td>
					</tr>
				</table>
				<input type="submit" value="登録" class="botan">
			</form>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>
	</div>
</body>
</html>