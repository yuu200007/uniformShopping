<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.Item"%>
<%@page import="util.MyFormat"%>

<%
Item item = (Item) request.getAttribute("item_info");
MyFormat MyFormatObj = new MyFormat();
%>


<html>
<head>
<title>商品情報変更</title>
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
						<a href="<%=request.getContextPath()%>/list">商品一覧</a> &nbsp;
						&nbsp; <a href="<%=request.getContextPath()%>/view/insert.jsp">商品登録</a>
					</p>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>商品情報変更</h2>
				</div>
			</div>
		</div>

		<!-- 書籍変更コンテンツ部分 -->
		<div id="main" class="container">

			<!-- 変更画面 -->
			<form method="post" enctype="multipart/form-data"
				action="<%=request.getContextPath()%>/update">
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
							<th>商品名</th>
							<td><%=item.getItemName()%></td>
							<td><input type="text" name="item_name"></td>
						</tr>
						<tr>
							<th>価格</th>
							<td><%=item.getPrice()%></td>
							<td><input type="text" name="price"></td>
						</tr>
						<tr>
							<th>在庫数</th>
							<td><%=item.getStock()%></td>
							<td><input type="text" name="stock"></td>
						</tr>
						<tr>
							<th>商品画像</th>
							<td><img
								src="<%=request.getContextPath()%>/img/<%=item.getImage()%>"
								alt="<%=item.getItemName()%>の画像"></td>
							<td><input type="file" name="image"></td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" name="item_id" value="<%=item.getItemId()%>">
				<input type="hidden" name="cmd" value="update"> <input
					type="submit" value="変更完了" class="botan">
			</form>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>
	</div>
</body>
</html>