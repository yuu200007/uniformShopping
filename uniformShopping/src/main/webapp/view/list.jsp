<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.Item"%>
<%@page import="bean.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="util.MyFormat"%>

<%
ArrayList<Item> itemList = (ArrayList<Item>) request.getAttribute("item_list");
MyFormat MyFormatObj = new MyFormat();
User user = (User) session.getAttribute("user");
%>

<html>
<head>
<title>商品一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>

<body>
	<!-- ブラウザ全体 -->
	<div id="wrap">

		<!--ヘッダー部分  -->
		<%@ include file="/common/header.jsp"%>

		<!-- メニュー部分 -->
		<div id="menu">
			<div class="container">
				<!-- ナビゲーション  -->
				<div id="navList">
					<ul>
						<%
						if (user == null) {
						%>
						<li><a href="<%=request.getContextPath()%>/view/login.jsp">[ログイン]</a></li>
						<li><a
							href="<%=request.getContextPath()%>/view/insertProfile.jsp">[会員登録]</a></li>
						<li><a href="<%=request.getContextPath()%>/view/showCart.jsp">[カート確認]</a></li>
						<%
						} else if ((user != null) && (user.getAuthority() == 1)) {
						%>
						<li><a href="<%=request.getContextPath()%>/logout">[ログアウト]</a></li>
						<li><a href="<%=request.getContextPath()%>/view/insert.jsp">[商品登録]</a></li>
						<%
						} else {
						%>
						<li><a href="<%=request.getContextPath()%>/logout">[ログアウト]</a></li>
						<li><a
							href="<%=request.getContextPath()%>/view/changeProfile.jsp">[会員情報変更]</a></li>
						<li><a
							href="<%=request.getContextPath()%>/showHistoryOrderedItem">[注文履歴]</a></li>
						<li><a href="<%=request.getContextPath()%>/view/showCart.jsp">[カート確認]</a></li>
						<%
						}
						%>
					</ul>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>商品一覧</h2>
				</div>
			</div>
		</div>

		<!-- 商品一覧のコンテンツ部分 -->
		<div id="main" class="container">

			<!-- 商品情報リスト -->
			<table class="list-table">
				<thead>
					<tr>
						<th>商品名</th>
						<th>商品画像</th>
						<th>在庫数</th>
						<th>価格</th>
						<th>&nbsp</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (itemList != null) {
						for (int i = 0; i < itemList.size(); i++) {
					%>
					<tr>
						<td><%=itemList.get(i).getItemName()%></td>
						<td><img
							src="<%=request.getContextPath()%>/img/<%=itemList.get(i).getImage()%>"
							alt="<%=itemList.get(i).getItemId()%>の書籍画像"></td>
						<td><%=itemList.get(i).getStock()%></td>
						<td><%=MyFormatObj.moneyFormat(itemList.get(i).getPrice())%></td>

						<%
						if ((user != null) && (user.getAuthority() == 1)) {
						%>
						<td>
							<form method="post" enctype="multipart/form-data" name="update"
								action="<%=request.getContextPath()%>/update">
								<input type="hidden" name="item_id"
									value=<%=itemList.get(i).getItemId()%>> <input
									type="hidden" name="cmd" value="update_view"> <input
									type="submit" value="変更">
							</form>
							<form method="post" name="delete"
								action="<%=request.getContextPath()%>/delete">
								<input type="hidden" name="item_id"
									value=<%=itemList.get(i).getItemId()%>> <input
									type="submit" value="削除">
							</form> </a>
						</td>
						<%
						} else {
						%>
						<form class="input_list"
							action="<%=request.getContextPath()%>/insertIntoCart">
							<td class="intocart"><input type="text" name="quantity">
								<input type="hidden" value=<%=itemList.get(i).getItemId()%>
								name="item_id"> <input type="submit" value="カートに入れる">
							</td>
						</form>
						<%
						}
						%>

					</tr>
					<%
					}
					}
					%>
				</tbody>
			</table>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>