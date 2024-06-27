<!-- 注文完了画面 -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.*,dao.*,util.*"%>
<%@page import="java.util.ArrayList"%>

<%
ArrayList<Order> order_list = (ArrayList<Order>) request.getAttribute("ordered_list");
MyFormat format = new MyFormat();
%>

<html>
<head>
<title>購入品確認</title>
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
						<a href="<%=request.getContextPath()%>/list">商品一覧</a>
					</p>
				</div>

				<!-- ページタイトル -->
				<div id="page_title">
					<h2>購入品確認</h2>
				</div>
			</div>
		</div>

		<!-- 購入品確認コンテンツ部分 -->
		<div id="main" class="container">

			<p class="bold">
				下記の商品を購入しました。<br> ご利用ありがとうございました。
			</p>

			<div style="margin-top: 40px">

				<table class="list-table2">
					<tr>
						<th>商品名</th>
						<th>単価</th>
						<th>個数</th>
					</tr>
					<%
					int total = 0;
					for (int i = 0; i < order_list.size(); i++) {
						Order order = order_list.get(i);
						total += order.getPrice() * order.getQuantity();
					%>
					<tr>
						<td><%=order.getItem_name()%></td>
						<td><%=format.moneyFormat(order.getPrice())%></td>
						<td><%=order.getQuantity()%></td>
					</tr>
					<%
					}
					%>
				</table>
				<hr>

				<table class="show-cart-sum">
					<tr>
						<th>合計</th>
						<td><%=format.moneyFormat(total)%></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- フッター部分 -->
		<%@ include file="/common/footer.jsp"%>

	</div>
</body>
</html>